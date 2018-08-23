package com.example.demo.datasource.tcc.intercepter.support;

import java.sql.SQLException;

import com.example.demo.datasource.tcc.exception.DistributedTransactionException;
import com.example.demo.datasource.tcc.message.TransactionMessageServiceImpl;
import com.example.demo.datasource.tcc.repository.TransactionIdGenerator;
import com.example.demo.datasource.tcc.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.example.demo.datasource.tcc.common.Constants;
import com.example.demo.datasource.tcc.common.TransactionStatusEnum;
import com.example.demo.datasource.tcc.context.TXContext;
import com.example.demo.datasource.tcc.context.support.ServiceExecutePayload;
import com.example.demo.datasource.tcc.context.support.TXContextSupport;
import com.example.demo.datasource.tcc.domain.RetryCount;
import com.example.demo.datasource.tcc.domain.TransactionInfo;
import com.example.demo.datasource.tcc.intercepter.ServiceIntercepter;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yangzz on 16/7/21.
 */
@Component
@Slf4j
public class ServiceIntercepterSupport implements ServiceIntercepter {

    private static final Logger log = LoggerFactory.getLogger(ServiceIntercepterSupport.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${tx.default.msg.retry.times}")
    private int defaultMsgRetryTimes;

    @Value("${tx.default.cancel.retry.times}")
    private int defaultCancelRetryTimes;

    @Value("${tx.default.confirm.retry.times}")
    private int defaultConfirmRetryTimes;

    @Value("${tx.diff.time}")
    private long diffTime = 1000;

    @Override
    public void tryService(TXContext ctx,ServiceExecutePayload bean) throws Throwable{
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setParentId(ctx.getParentId());

        // TODO 对于因子事务单元超时引起的事务状态不一致情况，由管控平台统一检查处理?
        /*transactionInfo.setTxId(Constants.TX_MAIN_ID);
        if (transactionRepository.find(transactionInfo).size() > 0) {
            throw new DistributedTransactionException("Received cancel command from main transaction unit, interrupt current transaction!");
        }*/

        if (!isTimeout(ctx)) {
            // 插入事务数据
            transactionInfo = new TransactionInfo();
            transactionInfo.setParentId(ctx.getParentId());
            transactionInfo.setTxId(TransactionIdGenerator.next());
            transactionInfo.setContext(JSON.toJSONString(bean)); // 当前调用上下文环境
            transactionInfo.setBusinessId(ctx.getSerialNumber());  // 业务流水号
            transactionInfo.setBusinessType(ctx.getBusinessType());  // 业务类型
            transactionInfo.setModuleId(bean.getModuleId());
            transactionInfo.setTxType(ctx.getTxType());  // TC | TCC
            transactionInfo.setTxStatus(TransactionStatusEnum.TRIED.getCode());
            transactionInfo.setRetriedCount(JSON.toJSONString(  // 设置重试次数
                    new RetryCount(defaultMsgRetryTimes, defaultCancelRetryTimes, defaultConfirmRetryTimes)));
            createTransactionInfo(transactionInfo);
            // 设置事务上下文
            ((TXContextSupport)ctx).setParentId(transactionInfo.getParentId());
            ((TXContextSupport)ctx).setTxId(transactionInfo.getTxId());
        } else {
            throw new DistributedTransactionException("Transaction timeout, abort this process. ctx=" + ctx);
        }
    }

    @Override
    public void confirmService(TXContext ctx) throws Throwable{
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setParentId(ctx.getParentId());
        transactionInfo.setTxId(ctx.getTxId());
        transactionInfo.setTxStatus(TransactionStatusEnum.CONFIRMED.getCode());
        transactionRepository.update(transactionInfo);
    }

    @Override
    public void cancelService(TXContext ctx) throws Throwable{
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setParentId(ctx.getParentId());
        transactionInfo.setTxId(ctx.getTxId());
        transactionInfo.setTxStatus(TransactionStatusEnum.CANCELLED.getCode());
        transactionRepository.update(transactionInfo);
    }

    private void createTransactionInfo(TransactionInfo transactionInfo) throws Throwable{
        int i = 2;
        while(i > 0) {
            try {
                transactionRepository.create(transactionInfo);
                break;
            } catch (SQLException e) {
                if (e.getSQLState().equals(Constants.KEY_23505)) {
                    log.warn("Create child transactionInfo record failed and retry:", e);
                    transactionInfo.setTxId(TransactionIdGenerator.next());
                    transactionRepository.create(transactionInfo);
                } else {
                    throw e;
                }
            }
            i--;
        }
    }

    private boolean isTimeout(TXContext ctx){
        if (ctx.getTimeout() <= 0 || ctx.getCallTime() == null) return false;

        if (System.currentTimeMillis() - diffTime - ctx.getCallTime().getTime() > ctx.getTimeout()) {
            return true;
        }

        return false;
    }
}
