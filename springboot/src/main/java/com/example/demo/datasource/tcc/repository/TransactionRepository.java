package com.example.demo.datasource.tcc.repository;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.datasource.tcc.domain.TransactionInfo;

/**
 * Created by changmingxie on 11/12/15.
 */
public interface TransactionRepository {

    int create(TransactionInfo transactionInfo) throws SQLException;

    int update(TransactionInfo transactionInfo);

    int delete(TransactionInfo transactionInfo);

    TransactionInfo findById(long txId);

    TransactionInfo directFindById(long txId);

    List<TransactionInfo> lockByModules(long parentId, List<String> modules);

    List<TransactionInfo> lock(TransactionInfo transactionInfo);

    List<TransactionInfo> find(TransactionInfo transactionInfo);

    /**
     * 事务恢复任务使用的查询SQL
     * @param date
     * @param txStatus
     * @param moduleId
     * @return
     */
    List<TransactionInfo> findSince(java.sql.Date date, Integer[] txStatus, String moduleId);
    
    List<TransactionInfo> listSince(java.sql.Date date);
}
