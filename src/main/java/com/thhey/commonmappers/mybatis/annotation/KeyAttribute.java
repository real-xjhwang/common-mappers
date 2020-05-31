package com.thhey.commonmappers.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于 entity 中的属性声明之前，主键注解
 * 仅支持单主键
 * 默认主键为 id 时不需要添加该注解
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyAttribute {
    /**
     * 是否是自增主键
     * @return boolean
     */
    boolean autoIncr() default false;
}
