package com.example.demo.guava;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.util.concurrent.*;

/**
 * Created by yafeiyf.wang on 2018/8/23.
 */
public class FutureTest implements Supplier<FutureTest> {
    ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

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
    public void futureCallAndRunTest() throws Exception {

        long startTime = System.currentTimeMillis();

        // call 异常
        ListenableFuture<Integer> callException = service.submit(() -> 1 / 0);

        // call 正常返回
        ListenableFuture<Integer> callResult = service.submit(() -> ThreadLocalRandom.current().nextInt(100));

        // run 正常执行
        ListenableFuture<?> runVoid = service
                .submit(() -> System.out.println("future task1 done....." + (System.currentTimeMillis() - startTime)));

        // run 异常
        ListenableFuture<Object> runException = service.submit(() -> {
            throw new RuntimeException("runnable exception");
        });

        // run 添加执行完成后返回数据
        ListenableFuture<String> runResult = service.submit(() -> System.out.println("runnable result"),
                "runnable result");

        addCallback(callException, callResult, runException, runResult, runVoid);

        addListener(callException);

        listListenableFuture(callException, callResult, runResult, runVoid);

        catchingAsync(callException);

        transform(callResult);

    }

    /**
     * 添加回调,正常执行时会调用onSuccess 失败会调用onFailure
     * 
     * @param listenableFuture
     */
    private void addCallback(ListenableFuture... listenableFuture) {

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

        List<ListenableFuture> listenableFutureList = Arrays.asList(listenableFuture);

        listenableFutureList
                .forEach(listenableFuture1 -> Futures.addCallback(listenableFuture1, futureCallback, service));

    }

    /**
     * addListener方法可以添加多个但是多个执行顺序不是顺序执行的可能结果 b -> c -> a;c -> a -> b;a -> b -> c
     * 
     * @param listenableFuture
     */
    private void addListener(ListenableFuture listenableFuture) {
        listenableFuture.addListener(() -> System.out.println("a"), service);
        listenableFuture.addListener(() -> System.out.println("b"), service);
        listenableFuture.addListener(() -> System.out.println("c"), service);
    }

    /**
     * 操作ListenableFuture列表信息,如果之中有一个异常会导致后续操作失败
     * 
     * @param listenableFuture
     */
    private void listListenableFuture(ListenableFuture... listenableFuture) {
        ListenableFuture listListenableFuture = Futures.allAsList(listenableFuture);
        try {
            listListenableFuture.addListener(() -> System.out.println("add listener"), service);
            List<Object> objects = (List<Object>) listListenableFuture.get();
            System.out.println(JSON.toJSONString(objects));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 只有当出现指定异常时会执行返回的ListenableFuture
     * 
     * @param runException
     */
    private void catchingAsync(ListenableFuture runException) {

        Futures.catchingAsync(runException, ArrayIndexOutOfBoundsException.class,
                a -> service.submit(() -> System.out.println("catchingAsync exception: " + a)));
    }

    /**
     * 第一个执行完成的结果,作为第二个任务执行的参数
     * 
     * @param listenableFuture
     * @throws Exception
     */
    public void transform(ListenableFuture listenableFuture) throws Exception {
        Function<Integer, Boolean> function = integer -> integer > 0;
        AsyncFunction<Integer, Boolean> asyncFunction = integer -> integer > 0 ? Futures.immediateFuture(Boolean.TRUE)
                : Futures.immediateFuture(Boolean.FALSE);
        ListenableFuture<Boolean> transform = Futures.transformAsync(listenableFuture, asyncFunction);

        System.out.println("transform:" + JSON.toJSONString(transform.get()));
    }

    // 测试
    public static void main(String[] args) throws Exception {
        FutureTest test = FutureTest.staticGet();
        test.futureCallAndRunTest();
        new Thread(() -> {
            try {
                test.futureCallAndRunTest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
