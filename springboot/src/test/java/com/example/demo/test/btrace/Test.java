package com.example.demo.test.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

public class Test {

    //每隔指定时间打印一下调用次数
    @OnMethod(clazz="com.qunar.twell.servlet.ApiServlet", method="doGet",
            location=@Location(value= Kind.RETURN))
    public static void m() {
        BTraceUtils.println("成功了");
    }
}
