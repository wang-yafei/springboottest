package com.example.demo.test.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Threads.jstack;
import static com.sun.btrace.BTraceUtils.println;

public class BtraceTest {

    @OnExit//当成程序退出时，执行一些命令
    public static void onexit(int code) {
        println("BTrace program exits! with code: " + code);
    }

    @Export //可以用来统计调用次数
    public static long counter;

    @OnMethod(clazz="com.test.BtraceTest", method="add",
            location=@Location(value= Kind.RETURN))
    public static void m(@Self Object self, int a,int b,@Return int result,@Duration long time) {
        BTraceUtils.println("参数： a: "+a+"  b: "+b);
        BTraceUtils.println("花费时间：  "+time*1.0/1000+"ms");
        jstack();//打印堆栈信息
        counter++;
    }

    @OnEvent("1")//通过事件触发，打印当前的程序调用次数
    public static void setL1() {
        BTraceUtils.println("executor count：  "+counter);
    }

    //监控程序是否走到第22行代码
    @OnMethod(clazz = "com.test.BtraceTest", location = @Location(value = Kind.LINE, line = 22))
    public static void onBind() {
        println("执行到第22行");
    }

    //每隔指定时间打印一下调用次数
    @OnTimer(5000)
    public static void run(){
        BTraceUtils.println("executor count：  "+counter);
    }


}
