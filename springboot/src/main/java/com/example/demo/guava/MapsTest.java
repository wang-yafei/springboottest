package com.example.demo.guava;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * Created by yafeiyf.wang on 2018/8/10.
 */
public class MapsTest {

    public static void main(String[] args) {

        HashMap<Object, Object> hashMap = Maps.newHashMap();//创建一个新Map
        Maps.newHashMapWithExpectedSize(3);//指定固定长度
        Maps.newHashMap(hashMap);//创建新Map时包含旧Map

    }
}
