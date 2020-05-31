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
     *
     * @return 设置是否为明细字段
     */
    boolean detailed() default false;

    /**
     * 字段说明
     *
     * @return 字段的注释说明
     */
    String value() default "";

    /**
     * 是否是必填字段
     *
     * @return boolean
     */
    boolean notNull() default false;

    /**
     * 字段长度，仅可变长类型设置
     * String 、byte[] 类型分别对应 mysql 中 varchar、varbinary类型，需要设置长度，默认50
     *
     * @return 字段长度
     */
    int length() default 0;

    /**
     * 是否唯一
     *
     * @return boolean
     */
    boolean unique() default false;
}
