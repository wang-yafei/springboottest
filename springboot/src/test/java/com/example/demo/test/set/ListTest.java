package com.example.demo.test.set;

import com.alibaba.fastjson.JSONArray;
import org.assertj.core.util.Lists;

import java.util.Collections;
import java.util.List;

public class ListTest {

    /**
     * 测试list集合打乱顺序,
     * 原理当长度小于5的时候直接交换两个位置的数据,前一个位置是顺序,后一个是个根据当前size的随机数,时间复杂度 2n + n * n / 2
     * 当长度大于5时,将集合转换成数据,交换数组中的两个数据的位置,后一个同样是一个随机值
     * 两种方式都会将集合中的元素遍历一遍
     */
    private static void shuffleTest(){
        List<String> orginList = Lists.newArrayList("a", "b", "c", "d");
        System.out.println(String.format("orginList" + JSONArray.toJSONString(orginList)));
        Collections.shuffle(orginList);
        System.out.println(String.format("newList" + JSONArray.toJSONString(orginList)));
    }

    public static void main(String[] args) {
        shuffleTest();// 测试list集合打乱顺序
    }


}
