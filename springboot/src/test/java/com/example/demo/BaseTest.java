package com.example.demo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseTest {

    private static void breakAndForTest(){

        List<Integer> collect = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        List<Integer> collect1 = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        AtomicInteger atomicInteger = new AtomicInteger();
        for (Integer i: collect) {
            for (Integer j: collect1 ) {
                if (j == 2){
                    break;
                }
                atomicInteger.incrementAndGet();
                System.out.println(i + "|" +j);
            }
        }
        System.out.printf(String.valueOf(atomicInteger.get()));
    }

    public static void main(String[] args) {
        //breakAndForTest();

       int a = 1;

        System.out.println(a << 1);

        String i = "1234567";

        System.out.println(i.replaceAll("","   ").trim());
    }


}
