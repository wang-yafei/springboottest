package com.example.demo.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Target 作用域
 */
public class TargetTest {

    /**
     * 作用到方法上
     */
    @Target(ElementType.METHOD)
    public @interface method{

    }

    /**
     * 用于类，接口，枚举,但不用于枚举值
     */
    @Target(ElementType.TYPE)
    public @interface  type{

    }

    /**
     * 用于注解类型
     */
    @Target(ElementType.ANNOTATION_TYPE)
    public @interface annotationType{

    }

    /**
     * 用于构造方法
     */
    @Target(ElementType.CONSTRUCTOR)
    public @interface constructot{

    }

    /**
     * 字段上，包括枚举值
     */
    @Target(ElementType.FIELD)
    public @interface field{

    }

    /**
     * 本地变量或catch语句
     */
    @Target(ElementType.LOCAL_VARIABLE)
    public @interface localVariable{

    }

    /**
     * 用于记录java文件的package信息
     */
    @Target(ElementType.PACKAGE)
    public @interface packageTest{

    }

    /**
     * 用于参数
     */
    @Target(ElementType.PARAMETER)
    public @interface parameter{

    }

    /**
     * 泛型，1.8新增
     */
    @Target(ElementType.TYPE_PARAMETER)
    public @interface  typeParaneter{

    }

    /**
     * 任意类类型，1.8新增
     */
    @Target(ElementType.TYPE_USE)
    public @interface  typeUser{

    }

}
