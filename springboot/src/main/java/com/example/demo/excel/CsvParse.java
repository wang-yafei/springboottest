package com.example.demo.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.http.HttpUtil;
import com.google.common.collect.Lists;

import java.io.*;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;


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
                    // 分割开来的即是对应的每个单元格，注意空的情况
                    String[] result = line.split(",");
                    String doGet = HttpUtil.doGet(getHttpUrl(result[0], result[1]));
                    JSONObject jsonObject = JSON.parseObject(doGet);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Collection<Object> values = data.values();
                    List<Object> objects = Lists.newArrayList(values);
                    JSONObject o = (JSONObject)objects.get(0);
                    String s = new String(o.get("roomType").toString().getBytes("gbk"), "GBK");
                    String roomType = line.concat(",").concat(s);
                    bw.write(roomType);
                }
                bw.newLine();
            }
        } finally {
            if (bReader != null) {
                bReader.close();
            }
        }
    }

    public static String  getHttpUrl(String seq,String roomType) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("http://l-roomclusterintl1.h.cn1.qunar.com:8080/test-cluster/testCtripCluster?hotelSeq=");
        sb.append(URLEncoder.encode(seq, "UTF-8"));
        sb.append("&wrapperId=&roomName=");
        sb.append( URLEncoder.encode(roomType, "UTF-8"));
        //如果是英文
        if (en.contains(roomType.substring(0,1).toUpperCase())){
            sb.append("&roomCnName=&roomEnName=");
        }else{
            sb.append("&roomEnName=&roomCnName=");
        }
        sb.append( URLEncoder.encode(roomType, "UTF-8"));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        CsvParse.readCSV("C:\\Users\\yafeiyf.wang\\Desktop\\parse.csv","C:\\Users\\yafeiyf.wang\\Desktop\\parse5.csv");
    }
}
