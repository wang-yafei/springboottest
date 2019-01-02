package com.example.demo.util.text;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseTextUtil {

    // 校验字符是否是中文,如果是中文返回true否则返回false
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 判断内容是否包含微信表情包
     * @return
     */
    public static boolean containWechatEmoticonPackage(String context){
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]]");
        Matcher m = p.matcher(context); // 操作的字符串
        return m.find();
    }



    public static void main(String[] args) throws UnsupportedEncodingException {

        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]]");
        Matcher m = p.matcher("\uD83D\uDE48\uD83D\uDE48\uD83D\uDE48\uD83D\uDE48"); // 操作的字符串
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);//从截取点将后面的字符串接上

        String s = sb.toString().replaceAll("\\+", "");

        System.out.println("\uD83D\uDE48\uD83D\uDE48\uD83D\uDE48\uD83D\uDE48");


        String s2 = "\uD83D\uDE48\uD83D\uDE48\uD83D\uDE48\uD83D\uDE48";


        System.out.println(new String(s2.getBytes("UTF-8"), "ISO-8859-1"));

        System.out.println(s);

        String s1 = new String("大爷哈哈哈".getBytes("UTF-8"), "ISO-8859-1");

        System.out.println(s1);

    }

}
