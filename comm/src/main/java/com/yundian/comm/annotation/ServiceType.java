package com.yundian.comm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yaowang on 2017/5/19.
 */

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RUNTIME)
public @interface ServiceType {
    Class<?> value();
}