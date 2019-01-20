package com.example.demo.util.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Dom4jUtil {

    protected static Logger logger = LoggerFactory.getLogger(Dom4jUtil.class);

    private static String parseTSD(String html) {
        String value = "";
        try {
            Document document = Jsoup.parse(html);
            value = document.select("input#tsdDetail").attr("value");

        } catch (Exception e) {
            logger.error("dom4j parse error", e);
        }
        return value;
    }

    public static void main(String[] args) throws IOException {
        String url = "D:\\test\\test.txt";
        String collect = Files.lines(Paths.get(url)).collect(Collectors.joining());
        System.out.println(collect);

        String s = parseTSD(collect);
        System.out.println("s:"+s);
    }


}
