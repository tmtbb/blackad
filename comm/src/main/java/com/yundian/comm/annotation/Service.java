package com.yundian.comm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yaowang on 2017/5/16.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Service {
    String value();
}
