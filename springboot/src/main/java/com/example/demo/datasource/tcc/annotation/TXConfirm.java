package com.example.demo.datasource.tcc.annotation;

import java.lang.annotation.*;

/**
 * Created by yangzz on 16/7/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
@Inherited
public @interface TXConfirm {
}
