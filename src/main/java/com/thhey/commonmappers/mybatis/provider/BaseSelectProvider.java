package com.thhey.commonmappers.mybatis.provider;

import com.thhey.commonmappers.mybatis.BaseEntity;
import com.thhey.commonmappers.mybatis.SqlFieldReader;
import com.thhey.commonmappers.util.ConsoleUtils;
import com.thhey.commonmappers.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供基础的查询语句
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class BaseSelectProvider {
    public static Map<String, String> selectPrefixWithDetailedMap = new ConcurrentHashMap<>(16);
    public static Map<String, String> selectPrefixMap = new ConcurrentHashMap<>(16);

    /**
     * 根据ID查询数据
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT 字段名 FROM 表名 WHERE id = #{id}
     */
    public static <T extends BaseEntity> String selectById(T entity) {
        String sql = getSelectPrefix(entity) + " WHERE id = #{id}";
        ConsoleUtils.info("selectById", sql, entity);
        return sql;
    }

    /**
     * 根据主键查询数据
     * 要求必须要有一个 @KeyAttribute 注解的字段，且必须要有值
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT 字段名 FROM 表名 WHERE key = #{value}
     */
    public static <T extends BaseEntity> String selectByKey(T entity) {
        try {
            String sql = getSelectPrefix(entity) + SqlFieldReader.getConditionByKeySuffix(entity);
            ConsoleUtils.info("selectByKey", sql, entity);
            return sql;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 查询所有数据，不使用查询条件
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT 字段名 FROM 表名
     */
    public static <T extends BaseEntity> String selectAll(T entity) {
        String sql = getSelectPrefix(entity);
        ConsoleUtils.info("selectAll", sql, entity);
        return sql;
    }

    /**
     * 查询数据，使用查询条件
     * 此查询为动态查询，所生成的查询语句不具有普遍性，所以不进行缓存
     * 传入的对象中带 @IndexAttribute 注解的字段有值的都作为查询条件
     * 传入对象中带 @SortAttribute 注解的字段作为排序字段
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT 字段名 FROM 表名 WHERE condition1 = #{value1} AND condition2 = #{value2} ORDER BY 字段名 ASC
     */
    public static <T extends BaseEntity> String selectByCondition(T entity) {
        String sql = getSelectPrefix(entity)
                + SqlFieldReader.getConditionByIndexSuffix(entity)
                + SqlFieldReader.getConditionBySortSuffix(entity);
        ConsoleUtils.info("selectByCondition", sql, entity);
        return sql;
    }

    /**
     * 查询记录总数
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT COUNT(1) FROM 表名
     */
    public static <T extends BaseEntity> String selectCount(T entity) {
        String sql = "SELECT COUNT(1) FROM " + SqlFieldReader.getTableName(entity);
        ConsoleUtils.info("selectCount", sql, entity);
        return sql;
    }

    /**
     * 根据条件查询记录总数
     * 传入的对象中带 @IndexAttribute 注解的字段有值的都作为查询条件
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT COUNT(1) FROM 表名 WHERE condition1 = #{value1} AND condition2 = #{condition2}
     */
    public static <T extends BaseEntity> String selectCountByCondition(T entity) {
        String sql = selectCount(entity) + SqlFieldReader.getConditionByIndexSuffix(entity);
        ConsoleUtils.info("selectCountByCondition", sql, entity);
        return sql;
    }

    /**
     * 不加条件的分页查询
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT 字段名 FROM 表名 LIMIT #{startRows},#{pageSize}
     */
    public static <T extends BaseEntity> String selectPageList(T entity) {
        String sql = selectAll(entity) + " LIMIT #{baseStartRows},#{basePageSize}";
        ConsoleUtils.info("selectPageList", sql, entity);
        return sql;
    }

    /**
     * 加条件的分页查询
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT 字段名 FROM 表名 WHERE condition1 = #{value1} AND condition2 = #{value2} ORDER BY 字段名 ASC LIMIT #{startRows},#{pageSize}
     */
    public static <T extends BaseEntity> String selectPageListByCondition(T entity) {
        String sql = selectByCondition(entity) + " LIMIT #{baseKyleStartRows},#{baseKylePageSize}";
        ConsoleUtils.info("selectPageListByCondition", sql, entity);
        return sql;
    }

    /**
     * 获取通用查询前缀
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT 所有字段 FROM 表名
     */
    private static <T extends BaseEntity> String getSelectPrefix(T entity) {
        String className = entity.getClass().getName();
        String sql;
        if (entity.isBaseDetailed()) {
            sql = selectPrefixWithDetailedMap.get(className);
        } else {
            sql = selectPrefixMap.get(className);
        }
        if (StringUtils.isEmpty(sql)) {
            sql = "SELECT " + SqlFieldReader.getFieldStr(entity) + " FROM " + SqlFieldReader.getTableName(entity) + " ";
            if (entity.isBaseDetailed()) {
                selectPrefixWithDetailedMap.put(className, sql);
            } else {
                selectPrefixMap.put(className, sql);
            }
        }
        return sql;

    }
}
