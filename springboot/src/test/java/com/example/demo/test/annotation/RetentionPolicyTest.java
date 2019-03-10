package com.example.demo.test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RetentionPolicyTest {

    /**
     * 级别最低,仅仅作为资源,编译后将忽略
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface  RetentionPolicySourceTest{

    }

    /**
     * 将会被编译成class文件,但是运行时不可用
     */
    @Retention(RetentionPolicy.CLASS)
    public @interface  RetentionPolicyClassTest{

    }

    /**
     * 运行时可用
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface  RetentionPolicyRunTiomeTest{

    }
}
