package com.example.demo.test.guava;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yafeiyf.wang on 2018/8/10.
 */
public class MapsTest {

    public static void main(String[] args) {
        //mapDiffTest();// 两个map数据做diff

        test1();

    }

    /**
     * 两个map数据做diff
     */
    private static void mapDiffTest(){
        HashMap<String, Object> map1 = Maps.newHashMap();
        map1.put("a", 3);
        map1.put("b", 2);
        map1.put("d", 2);
        HashMap<String, Object> map2 = Maps.newHashMap();
        map2.put("a", 1);
        map2.put("b", 2);
        map2.put("c", 3);

        MapDifference<String, Object> difference = Maps.difference(map1, map2);
        Map<String, Object> stringObjectMap = difference.entriesOnlyOnLeft();
        System.out.println(JSONObject.toJSONString(stringObjectMap));// {"d":2}
        Map<String, Object> stringObjectMap1 = difference.entriesOnlyOnRight();
        System.out.println(JSONObject.toJSONString(stringObjectMap1));// {"c":3}
        Map<String, Object> stringObjectMap2 = difference.entriesInCommon();
        System.out.println(JSONObject.toJSONString(stringObjectMap2));// {"b":2}
        Map<String, MapDifference.ValueDifference<Object>> stringValueDifferenceMap = difference.entriesDiffering();
        MapDifference.ValueDifference<Object> a = stringValueDifferenceMap.get("a");
        Object o = a.leftValue();
        Object o1 = a.rightValue();
        System.out.println(JSONObject.toJSONString(stringValueDifferenceMap));// {"a":{}}
        System.out.println(o);// 3
        System.out.println(o1);// 1
    }

    private static void test1(){

        Map<String,Integer> resultMap = Maps.newLinkedHashMap();

        resultMap.put("c", 1); // 低价酒店列表
        resultMap.put("b", 2);  // 经济酒店列表
        resultMap.put("s", 3);

        System.out.println(JSONObject.toJSONString(resultMap));
    }


}
