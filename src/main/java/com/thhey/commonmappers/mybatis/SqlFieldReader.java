package com.thhey.commonmappers.mybatis;

import com.thhey.commonmappers.mybatis.annotation.*;
import com.thhey.commonmappers.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取注解中的表明、字段名等
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class SqlFieldReader {
    /**
     * 获取表名
     * @param entity 实体类
     * @param <T> 实体类型
     * @return 表名
     */
    public static <T extends BaseEntity> String getTableName(T entity) {
        TableAttribute tableAttribute = entity.getClass().getAnnotation(TableAttribute.class);
        if (tableAttribute == null) {
            return null;
        }
        return tableAttribute.name();
    }

    /**
     * 将所有字段名以逗号拼接起来并返回
     * 注解 @FieldAttribute 表示要查询的字段名
     * 当所有属性都没有 @FieldAttribute 注解的时候，将所有属性名作为字段名
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return 需要查询的所有字段
     */
    public static <T extends BaseEntity> String getFieldStr(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();

        /*
        带有注解的字段
         */
        StringBuilder annotationFields = new StringBuilder();

        /*
        所有的字段
         */
        StringBuilder allFields = new StringBuilder();

        for (Field field : fields) {
            allFields.append(field.getName()).append(",");
            if (field.getAnnotation(FieldAttribute.class) != null) {
                FieldAttribute fieldAttribute = field.getAnnotation(FieldAttribute.class);
                // 如果查询明细字段，返回明细字段
                if (entity.isBaseDetailed()) {
                    annotationFields.append(field.getName()).append(",");
                    // 如果不查询明细字段，不返回明细字段
                } else {
                    if (!fieldAttribute.detailed()) {
                        annotationFields.append(field.getName()).append(",");
                    }
                }
            }
        }
        if (annotationFields.length() > 0) {
            return annotationFields.substring(0, annotationFields.length() - 1);
        } else if (allFields.length() > 0) {
            return allFields.substring(0, allFields.length() - 1);
        } else {
            return null;
        }
    }

    /**
     * 获取索引的查询条件
     * 实体对象中带有 @IndexAttribute 注解的字段有值都将作为查询条件
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return WHERE name = #{name} OR controllerName = #{controllerName}
     */
    public static <T extends BaseEntity> String getConditionByIndexSuffix(T entity) {
        String condition;
        if (entity.getBaseUseAnd() == null) {
            return "";
        }
        if (entity.getBaseUseAnd()) {
            condition = "AND";
        } else {
            condition = "OR";
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" WHERE ");
        try {
            for (Field field : fields) {
                if (field.getAnnotation(IndexAttribute.class) != null) {
                    if (SqlFieldReader.hasValue(entity, field.getName())) {
                        builder.append(field.getName())
                                .append(" = #{").append(field.getName()).append("} ")
                                .append(condition).append(" ");
                    }
                }
            }
            int index = builder.lastIndexOf(condition);
            if (index < 0) {
                return "";
            }
            return builder.substring(0, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取主键的查询条件
     * 传入的对象必须满足以下条件：
     * 1. 必须有且只有一个带有@keyAttribute注解的字段，如果有多个，只取第一个
     * 2. 带有@KeyAttribute注解的字段必须有值
     * 这是为了避免产生因为没有设置 @KeyAttribute 注解而造成全部数据修改或删除的问题
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return WHERE userId = #{userId}
     * @throws BaseException 自定义异常
     */
    public static <T extends BaseEntity> String getConditionByKeySuffix(T entity) throws BaseException {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" WHERE ");
        try {
            for (Field field : fields) {
                if (field.getAnnotation(KeyAttribute.class) != null) {
                    if (hasValue(entity, field.getName())) {
                        builder.append(field.getName())
                                .append(" =#{").append(field.getName()).append("} ");
                    } else {
                        throw new BaseException("@KeyAttribute修饰的字段不能为空");
                    }
                    break;
                }
            }
            int index = builder.lastIndexOf("=");
            if (index < 0) {
                throw new BaseException("没有找到@KeyAttribute修饰的字段");
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }

    /**
     * 获取排序的查询条件
     * 实体对象中带有 @SortAttribute 注解的字段且有值的话将作为查询条件
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return ORDER BY ASC
     */
    public static <T extends BaseEntity> String getConditionBySortSuffix(T entity){
        String condition;
        if(entity.getBaseUseASC() == null){
            return "";
        }
        if(entity.getBaseUseASC()){
            condition = "ASC";
        }else {
            condition = "DESC";
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" ORDER BY ");
        try {
            for(Field field:fields){
                if(field.getAnnotation(SortAttribute.class) != null){
                    builder.append(field.getName()).append(" ")
                            .append(condition).append(",");

                }
            }
            int index = builder.lastIndexOf(",");
            if(index < 0){
                return "";
            }
            return builder.substring(0,index);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取所有字段列表
     * 读取类中带@FieldAttribute注解的字段，如果都没有带该注解，则返回类中所有字段
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return {id,name}
     */
    public static <T extends BaseEntity> List<String> getFields(T entity){
        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> fieldList = new ArrayList<>();
        List<String> allList = new ArrayList<>();
        //带 @FieldAttribute 注解的属性名
        for(Field field:fields){
            allList.add(field.getName());
            if(field.getAnnotation(FieldAttribute.class) != null){
                fieldList.add(field.getName());
            }
        }
        if(fieldList.size() == 0){
            return allList;
        }
        return fieldList;
    }

    /**
     * 判断一个对象的指定字段有没有值
     * @param entity 实体对象
     * @param fieldName 对象的字段名
     * @param <T> 实体类型
     * @return 值存在且不为 null ：返回true；否则返回false
     */
    public static <T> boolean hasValue(T entity, String fieldName) {
        try {
            Method method = entity.getClass().getMethod("get" + StringUtils.captureName(fieldName));
            return method.invoke(entity) != null;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}
