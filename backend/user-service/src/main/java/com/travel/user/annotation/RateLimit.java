package com.travel.user.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 每秒请求数
     */
    double qps() default 10.0;

    /**
     * 限流类型：接口级别或IP级别
     */
    Type type() default Type.IP;

    /**
     * 限流类型枚举
     */
    enum Type {
        /**
         * 接口级别限流
         */
        INTERFACE,
        
        /**
         * IP级别限流
         */
        IP
    }
}
