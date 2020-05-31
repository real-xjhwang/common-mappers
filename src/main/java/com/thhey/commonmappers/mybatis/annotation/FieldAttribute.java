package com.thhey.commonmappers.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于 entity 中的属性声明之前，字段名注解
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAttribute {
    /**
     * 是否是明细字段，如果是明细字段，在查询列表时不显示
     * @return 设置是否为明细字段
     */
    boolean detailed() default false;
}
