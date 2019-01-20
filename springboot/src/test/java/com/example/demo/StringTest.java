package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class StringTest {

    private static final String REQUEST_URI = "https://www.expedia-cn.com/m/api/hotel/offers?clientid=expedia.app.ios.phone:17.29&forceNoRedir=1&checkOutDate=$$&sourceType=mobileapp&shopWithPoints=false&langid=2052&forceV2Search=true&hotelId=$$&room1=1&priceType=TOTAL_PRICE&checkInDate=$$&currency=CNY&rfrr=Header.Currency.CNY";


    public static String trim(){



        String a = "    （abc）e），（d，()";
        String s = a.replaceAll(" ", "")
                .replaceAll("（", "(")
                .replaceAll("）", ")")
                .replaceAll("，", ",");
        return  s;
    }

    public static void main(String[] args) {

        String s = "id=id=id=a=11757409";

        String s1 = "11757409";

        List<String> list = Arrays.asList(s.split("\\="));
        List<String> list1 = Arrays.asList(s1.split("\\="));

        System.out.println(list.get(list.size()-1));
        System.out.println(list1.get(list1.size()-1));


    }


    protected static String fillString(String str, Vector<String> args) {
        if (args == null || args.size() == 0) {
            return str;
        }

        if (str == null || str.length() == 0)
            return str;

        int startPos = 0;
        int endPos;
        String res = "";
        int i = 0;
        while ((endPos = str.indexOf("$$", startPos)) != -1) {
            res += str.substring(startPos, endPos) + args.get(i);
            startPos = endPos + 2;
            i++;
        }
        res += str.substring(startPos);
        return res;
    }

}
