package com.example.demo.test.collection;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SetsTest {


    private static void baseTest(){

        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 5, 6);
        Set<Integer> set2 = Sets.newHashSet(1, 2, 3, 4);

        ImmutableSet<Integer> integers = Sets.intersection(set1, set2).immutableCopy();
        System.out.println(JSONObject.toJSONString(integers));// 取交集 [1,2,3]
        ImmutableSet<Integer> integers1 = Sets.difference(set1, set2).immutableCopy();
        System.out.println(JSONObject.toJSONString(integers1));// 取差集 只取set1多的部分 [5,6]
        ImmutableSet<Integer> integers2 = Sets.symmetricDifference(set1, set2).immutableCopy();
        System.out.println(JSONObject.toJSONString(integers2));// 取两个不相同的部分 [5,6,4]

    }


    private static void effectivenessCompar(){

        Set<Integer> collect = IntStream.range(0, 10000).mapToObj(i -> i).collect(Collectors.toSet());
        Set<Integer> collect1 = IntStream.range(5, 10005).mapToObj(i -> i).collect(Collectors.toSet());

        long l = System.currentTimeMillis();
        CollectionUtils.intersection(collect, collect1);
        System.out.println(System.currentTimeMillis() - l);

        long l1 = System.currentTimeMillis();
        Sets.SetView<Integer> intersection = Sets.intersection(collect, collect1);
        System.out.println(System.currentTimeMillis() - l1);

    }


    public static void main(String[] args) {

        //baseTest();
        effectivenessCompar();



    }


}
