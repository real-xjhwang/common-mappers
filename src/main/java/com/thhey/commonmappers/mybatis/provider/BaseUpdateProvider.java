package com.thhey.commonmappers.mybatis.provider;

import com.thhey.commonmappers.mybatis.BaseEntity;
import com.thhey.commonmappers.mybatis.SqlFieldReader;
import com.thhey.commonmappers.util.ConsoleUtils;

import java.util.List;

/**
 * 提供基础更新语句
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class BaseUpdateProvider {
    /**
     * 根据 id 更新数据，数值不更新，要求数据有 id 字段
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return UPDATE 表名 SET field1 = #{value1},field2 = #{value2} WHERE id = #{id}
     */
    public static <T extends BaseEntity> String updateById(T entity) {
        String sql = getUpdatePrefix(entity) + " WHERE id = #{id}";
        ConsoleUtils.info("updateById", sql, entity);
        return sql;
    }

    /**
     * 根据主键更新数据，空值不更新，要求数据至少有一个主键并且有值
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return UPDATE 表名 SET field1 = #{value1},field2=#{value2} WHERE key = #{value}
     */
    public static <T extends BaseEntity> String updateByKey(T entity) {
        try {
            String sql = getUpdatePrefix(entity) + SqlFieldReader.getConditionByKeySuffix(entity);
            ConsoleUtils.info("updateByKey", sql, entity);
            return sql;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取更新操作的前缀部分
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return UPDATE 表名 SET
     */
    private static <T extends BaseEntity> String getUpdatePrefix(T entity) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(SqlFieldReader.getTableName(entity)).append(" SET ");
        List<String> fields = SqlFieldReader.getFields(entity);
        try {
            for (String field : fields) {
                if (SqlFieldReader.hasValue(entity, field)) {
                    builder.append(field).append(" = #{")
                            .append(field).append("} ").append(",");
                }
            }
            return builder.substring(0, builder.lastIndexOf(","));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
