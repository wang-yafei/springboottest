package com.example.demo.guava;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.util.concurrent.*;

/**
 * Created by yafeiyf.wang on 2018/8/23.
 */
public class FutureTest implements Supplier<FutureTest> {
    ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));

    @Override
    public FutureTest get() {
        return new FutureTest();
    }

    /**
     * Supplier方式获取单例
     * 
     * @return
     */
    public static FutureTest staticGet() {
        Supplier<FutureTest> s = new FutureTest();
        return Suppliers.memoize(s).get();
    }

    /**
     * 测试guavafuture正常执行和回调
     */
    public void futureCallAndRunTest() {

        long startTime = System.currentTimeMillis();

        // call 异常
        ListenableFuture<Integer> callException = service.submit(() -> {
            Thread.sleep(1000);
            throw new RuntimeException("callable exception");
        });

        // call 正常返回
        ListenableFuture<Integer> callResult = service.submit(() -> ThreadLocalRandom.current().nextInt(100));

        // run 正常执行
        ListenableFuture<?> runVoid = service.submit(() -> {
            System.out.println("future task1 done....." + (System.currentTimeMillis() - startTime));
        });

        // run 异常
        ListenableFuture<Object> runException = service.submit(() -> {
            throw new RuntimeException("runnable exception");
        });

        // run 添加执行完成后返回数据
        ListenableFuture<String> runResult = service.submit(() -> {
            System.out.println("runnable result");
        }, "runnable result");

        // 任务执行完后回调方法
        FutureCallback<Object> futureCallback = new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {// 成功
                System.out.println("main task success,result:" + JSON.toJSONString(result));
            }

            @Override
            public void onFailure(Throwable t) {// 失败
                System.out.println("main task fail:" + t.getMessage());
            }
        };
        Futures.addCallback(callException, futureCallback, service);
        Futures.addCallback(callResult, futureCallback, service);
        Futures.addCallback(runVoid, futureCallback, service);
        Futures.addCallback(runResult, futureCallback, service);
        Futures.addCallback(runException, futureCallback, service);
    }

    // 测试
    public static void main(String[] args) throws Exception {
        FutureTest test = FutureTest.staticGet();
        test.futureCallAndRunTest();
        new Thread(() -> test.futureCallAndRunTest()).start();
    }

}
