package com.example.demo.util.encrypt;

import java.net.URLEncoder;

public class EscapeUtil {

    public static String encodeURI(String uri) {
        try {
            return URLEncoder.encode(uri, "UTF-8");
        } catch (Exception e) {
            return uri;
        }
    }

    public static void main(String[] args) {

        String s = "</script><marquee%20onwheel=alert`123`>";

        String s1 = encodeURI(s);
        System.out.println(s);
        System.out.println(s1);

    }



}
