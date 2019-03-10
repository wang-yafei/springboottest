package com.example.demo.hole;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * 预防xss漏洞
 */
public class XsslHttpServletRequestWrapper extends HttpServletRequestWrapper {

    protected static Logger logger = LoggerFactory.getLogger(XsslHttpServletRequestWrapper.class);

    /**
     * 定义正则表达式
     */
    private static final Pattern SCRIPT_PATTERN_1 = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern SCRIPT_PATTERN_2 = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SCRIPT_PATTERN_3 = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SCRIPT_PATTERN_4 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern SCRIPT_PATTERN_5 = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SCRIPT_PATTERN_6 = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SCRIPT_PATTERN_7 = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SCRIPT_PATTERN_8 = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static final Pattern SCRIPT_PATTERN_9 = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
    private static final Pattern SCRIPT_PATTERN_10 = Pattern.compile("onerror", Pattern.CASE_INSENSITIVE);
    private static final Pattern SCRIPT_PATTERN_11 = Pattern.compile("confirm", Pattern.CASE_INSENSITIVE);
    private static final Pattern SCRIPT_PATTERN_12 =  Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SCRIPT_PATTERN_13 = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);


    HttpServletRequest xssRequest = null;

    public XsslHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        xssRequest = request;
    }


    @Override
    public String getParameter(String name) {
        String value = super.getParameter(replaceXSS(name));
        if (StringUtils.isNotEmpty(value)) {
            value = replaceXSS(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(replaceXSS(name));
        if(ArrayUtils.isNotEmpty(values)){
            for(int i =0; i< values.length ;i++){
                values[i] = replaceXSS(values[i]);
            }
        }
        return values;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(replaceXSS(name));
        if (StringUtils.isNotEmpty(value)) {
            value = replaceXSS(value);
        }
        return value;
    }
    /**
     * 去除待带script、src的语句，转义替换后的value值
     */
    public static String replaceXSS(String value) {
        if (value != null) {
            try{
                value = value.replace("+","%2B");   //'+' replace to '%2B'
                value = URLDecoder.decode(value, "utf-8");
            }catch(Exception e){
                logger.error("xss htto servlet url decoder error", e);
            }

            // 避免出现空的情况
            value = value.replaceAll("\0", "");

            // 避免出现<script>
            value = SCRIPT_PATTERN_1.matcher(value).replaceAll("");

            // 避免 anything in a src='...'
            value = SCRIPT_PATTERN_2.matcher(value).replaceAll("");
            value = SCRIPT_PATTERN_3.matcher(value).replaceAll("");

            // 移除所有无效的 </script>标签
            value = SCRIPT_PATTERN_4.matcher(value).replaceAll("");
            value = SCRIPT_PATTERN_5.matcher(value).replaceAll("");

            // 避免 eval(...)
            value = SCRIPT_PATTERN_6.matcher(value).replaceAll("");

            // 避免 e­xpression(...) e­xpressions
            value = SCRIPT_PATTERN_7.matcher(value).replaceAll("");

            // Avoid javascript:... e­xpressions
            value = SCRIPT_PATTERN_8.matcher(value).replaceAll("");

            // 避免alert相关
            value = SCRIPT_PATTERN_9.matcher(value).replaceAll("");
            value = SCRIPT_PATTERN_10.matcher(value).replaceAll("");
            value = SCRIPT_PATTERN_11.matcher(value).replaceAll("");

            // 避免 onload= e­xpressions
            value = SCRIPT_PATTERN_12.matcher(value).replaceAll("");

            // 避免出现 vbscript
            value = SCRIPT_PATTERN_13.matcher(value).replaceAll("");
            // 替换其他特殊字符
            value = value.replaceAll("<","").replaceAll(">","").replaceAll("\"","").replaceAll("'","");
        }
        return filter(value);
    }

    /**
     * 过滤特殊字符
     */
    public static String filter(String value) {
        if (value == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(value.length());
        for (int i=0; i<value.length(); ++i) {
            switch (value.charAt(i)) {
            case '<':
                result.append("<");
                break;
            case '>':
                result.append(">");
                break;
            case '"':
                result.append("\"");
                break;
            case '\'':
                result.append("'");
                break;
            case '%':
                result.append("%");
                break;
            case ';':
                result.append(";");
                break;
            case '(':
                result.append("(");
                break;
            case ')':
                result.append(")");
                break;
            case '&':
                result.append("&");
                break;
            case '+':
                result.append("+");
                break;
            default:
                result.append(value.charAt(i));
                break;
            }
        }
        return result.toString();
    }

}
