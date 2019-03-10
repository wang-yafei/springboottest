package com.example.demo.test.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTest {

    private static  void test1() throws Exception {

        List<Callable<Boolean>> futures = new ArrayList<Callable<Boolean>>();


        for (int j = 1; j < 100; j++) {
            int i = j;
            Callable<Boolean> callable = new Callable<Boolean>() {

                @Override public Boolean call() throws Exception {
                    Thread.sleep(100 * i);
                    System.out.println("i");
                    return false;
                }
            };
            futures.add(callable);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Boolean>> futures1 = executorService.invokeAll(futures);

        for(Future<Boolean> fu : futures1) {
            if(!fu.get()) {
                throw new Exception("at least one fetch productInfo thread runs failed");
            }
        }

    }

    public static void main(String[] args) throws Exception {
        try {
            test1();
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
