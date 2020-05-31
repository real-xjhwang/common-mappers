package com.thhey.commonmappers.mybatis.provider;

import com.thhey.commonmappers.mybatis.BaseEntity;
import com.thhey.commonmappers.mybatis.SqlFieldReader;
import com.thhey.commonmappers.util.ConsoleUtils;
import com.thhey.commonmappers.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供常用插入语句
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class BaseInsertProvider {
    /**
     * 缓存基础insert语句
     * ConcurrentHashMap: 线程安全的哈希表
     */
    public static Map<String, String> insertMap = new ConcurrentHashMap<>(16);
    /**
     * 缓存插入并且获取自增ID的insert语句
     * ConcurrentHashMap: 线程安全的哈希表
     */
    public static Map<String, String> insertAndReturnKeyMap = new ConcurrentHashMap<>(16);

    /**
     * 基础插入语句
     * 读取对象的所有字段属性，生成insert语句
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return sql
     */
    public static <T extends BaseEntity> String insert(T entity) {
        String className = entity.getClass().getName();
        String sql = insertMap.get(className);

        if (StringUtils.isEmpty(sql)) {
            String fieldStr = SqlFieldReader.getFieldStr(entity);

            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO")
                    .append(SqlFieldReader.getTableName(entity)).append(" ")
                    .append("(").append(fieldStr).append(") ")
                    .append("VALUES(");

            StringBuilder valuesStr = new StringBuilder();
            assert fieldStr != null;
            String[] arrays = fieldStr.split(",");
            for (String str : arrays) {
                valuesStr.append("#{").append(str).append("}").append(",");
            }
            builder.append(valuesStr.substring(0, valuesStr.length() - 1))
                    .append(") ");
            sql = builder.toString();
            insertMap.put(className, sql);
        }
        ConsoleUtils.info("insert", sql, entity);
        return sql;
    }

    /**
     * 返回自增ID的插入语句
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return sql
     */
    public static <T extends BaseEntity> String insertAndReturnKey(T entity) {
        String className = entity.getClass().getName();
        String sql = insertAndReturnKeyMap.get(className);
        if (StringUtils.isEmpty(sql)) {
            String fieldStr = SqlFieldReader.getFieldStr(entity);
            assert fieldStr != null;
            String[] arrays = fieldStr.split(",");

            StringBuilder builder = new StringBuilder();

            StringBuilder valuesStr = new StringBuilder();

            builder.append("INSERT INTO ")
                    .append(SqlFieldReader.getTableName(entity)).append(" ")
                    .append("(");
            for (String str : arrays) {
                if ("id".equals(str)) {
                    continue;
                }
                valuesStr.append(str).append(",");
            }
            builder.append(valuesStr.substring(0, valuesStr.length() - 1));

            builder.append(") ").append("VALUES(");

            valuesStr = new StringBuilder();
            for (String str : arrays) {
                if ("id".equals(str)) {
                    continue;
                }
                valuesStr.append("#{").append(str).append("}").append(",");
            }
            builder.append(valuesStr.substring(0, valuesStr.length() - 1))
                    .append(") ");
            sql = builder.toString();
            insertAndReturnKeyMap.put(className, sql);
        }
        ConsoleUtils.info("insertAndReturnKey", sql, entity);
        return sql;
    }

    public static void main(String[] args) {

    }
}
