package com.example.demo.util;

public class SystemPropertyUtil {

    private volatile static String osName = System.getProperty("os.name").toLowerCase();

    /**
     * 单例获取当前系统是什么系统(windows、linux)
     * @return
     */
    public static String getOsName(){
        if (osName == null){
            synchronized (SystemPropertyUtil.class){
                osName = System.getProperty("os.name").toLowerCase();
            }
        }
        return osName;
    }

}
