package com.example.demo.util.number;

public class NumberUtil {

    public static String numberToString(){
        // 0 代表前面补充0
        // 10代表长度为10
        // d 代表参数为正数型
        return String.format("a%010d", 1);
    }

    public static void main(String[] args) {
        System.out.println(numberToString());
    }


}
