package com.example.demo.excel;

import java.io.*;


/**
 * Created by yafeiyf.wang on 2018/12/12.
 */
public class CsvParse {

    public static BufferedReader bReader;

    private static String en = "QWERTYUIOPASDFGHJKLZXCVBNM";

    public static void readCSV(String path, String copyPath) throws Exception {
        File file = new File(path);
        File fileWrite = new File(copyPath);
        try {
            bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileWrite),"gbk")); // 附加
            String line = "";
            // 忽略第一行标题
            for (int i = 0; i < 1; i++) {
                line = bReader.readLine();
                line.contains(",new_type");
                bw.write(line);
                bw.newLine();
            }
            while ((line = bReader.readLine()) != null) {
                if (line.trim() != "") {

                }
                bw.newLine();
            }
        } finally {
            if (bReader != null) {
                bReader.close();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        CsvParse.readCSV("C:\\Users\\yafeiyf.wang\\Desktop\\parse.csv","C:\\Users\\yafeiyf.wang\\Desktop\\parse5.csv");
    }
}
