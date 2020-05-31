package com.thhey.commonmappers.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于 entity 的类声明之前，表名注解
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableAttribute {
    /**
     * 表名
     * @return 表名
     */
    String name();

    /**
     * 表注释
     * @return 表的描述信息
     */
    String comment() default "";
}
