package com.thhey.commonmappers.mybatis.provider;

import com.thhey.commonmappers.mybatis.BaseEntity;
import com.thhey.commonmappers.mybatis.SqlFieldReader;
import com.thhey.commonmappers.util.ConsoleUtils;
import com.thhey.commonmappers.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供常用删除语句
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class BaseDeleteProvider {
    public static Map<String, String> deleteByIdMap = new ConcurrentHashMap<>(16);

    /**
     * 根据 ID 删除数据，要求必须要有 ID 字段
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return DELETE FROM tableName WHERE id = #{id}
     */
    public static <T extends BaseEntity> String deleteById(T entity) {
        String className = entity.getClass().getName();
        String sql = deleteByIdMap.get(className);
        if (StringUtils.isEmpty(sql)) {
            sql = getDeletePrefix(entity) + " WHERE id = #{id} ";
            deleteByIdMap.put(className, sql);
        }
        ConsoleUtils.info("deleteById", sql, entity);
        return sql;
    }

    /**
     * 根据主键删除数据，要求必须要有主键字段
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return DELETE FROM tableName WHERE key = #{value}
     */
    public static <T extends BaseEntity> String deleteByKey(T entity) {
        try {
            String sql = getDeletePrefix(entity) + SqlFieldReader.getConditionByKeySuffix(entity);
            ConsoleUtils.info("deleteByKey", sql, entity);
            return sql;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据条件删除，该查询为动态查询，不可缓存
     * 传入的对象中带 @IndexAttribute 注解的字段有值的都作为查询条件
     * 多个查询条件用And连接
     * and 多个查询条件组合方式 null:不指定查询条件  true:多个查询条件用AND连接  false:多个查询条件用OR连接
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return DELETE FROM tableName WHERE name = #{name} AND serviceName = #{serviceName}
     */
    public static <T extends BaseEntity> String deleteByCondition(T entity) {
        String sql = getDeletePrefix(entity) + SqlFieldReader.getConditionByIndexSuffix(entity);
        ConsoleUtils.info("deleteByCondition", sql, entity);
        return sql;
    }

    /**
     * 生成删除语句的前一部分
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return DELETE FROM tableName
     */
    private static <T extends BaseEntity> String getDeletePrefix(T entity) {
        return "DELETE FROM " + SqlFieldReader.getTableName(entity) + " ";
    }

    public static void main(String[] args) {

    }
}
