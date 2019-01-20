package com.example.demo.test.jdk8;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamTest {

    /**
     * jdk8 map相互转换
     */
    private static void mapToMapTest() {
        Map<String, String> newMap = Maps.newHashMap();
        newMap.put("a", "123");
        newMap.put("b", "235");


        Map<String, Integer> collectMap = newMap.entrySet().parallelStream().collect(
                Collectors.toMap(orginMap -> orginMap.getKey(), orginMap -> NumberUtils.toInt(orginMap.getValue())));

        System.out.println(newMap.entrySet().stream().isParallel());

        collectMap.entrySet().parallelStream()
                .forEach(map -> System.out.println("key: " + map.getKey() + " value： " + map.getValue()));
    }

    /**
     * list 转map
     */
    private static void listToMap() {

        List<String> lists = Lists.newArrayList("1", "2", "3");
        Map<String, String> collectMap = lists.parallelStream()
                .collect(Collectors.toMap(orginList -> orginList.toString(), orginList -> orginList.toString()));
        collectMap.entrySet().parallelStream()
                .forEach(map -> System.out.println("key: " + map.getKey() + " value： " + map.getValue()));

    }

    /**
     * 是否使用并行Stream (parallelStream)
     */
    private static void isParallel(){

        IntStream intStream = IntStream.range(0, 100);

        System.out.println(intStream.isParallel());
        System.out.println(intStream.parallel().isParallel());

    }

    /**
     *  filter
     */
    private static void filter(){
        List<String> l = Lists.newArrayList();
        l.add("");
        l.add(null);
        l.add(" 1");
        int size = l.stream().filter(ll -> StringUtils.isNotEmpty(ll)).collect(Collectors.toList()).size();
        System.out.println(size);
    }

    private static void optional(){
        String a = null;
        String a1 = Optional.ofNullable(a).map(String::trim).orElse("A");
        System.out.println(a1);
        String s = Optional.ofNullable(a).orElseGet(() -> {
            String c = "A";
            return "A"+"B" + c;
        });
        System.out.println(s);

    }


    public static void main(String[] args) {
        mapToMapTest();
        listToMap();
        isParallel();
        filter();
        optional();
    }

}
