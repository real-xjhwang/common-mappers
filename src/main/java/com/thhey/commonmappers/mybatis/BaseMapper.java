package com.thhey.commonmappers.mybatis;

import com.thhey.commonmappers.mybatis.provider.BaseDeleteProvider;
import com.thhey.commonmappers.mybatis.provider.BaseInsertProvider;
import com.thhey.commonmappers.mybatis.provider.BaseSelectProvider;
import com.thhey.commonmappers.mybatis.provider.BaseUpdateProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * 通用Mapper
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public interface BaseMapper<T extends BaseEntity> {
    /**
     * 插入操作
     * 将实体类的所有字段和字段的值分别列出来，适用于主键不是自增的表
     *
     * @param entity 实体对象
     * @return insert的执行结果
     * @throws DuplicateKeyException 当唯一字段重复插入时，会抛出此异常
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    Integer baseInsert(T entity) throws DuplicateKeyException;

    /**
     * 插入数据并返回自增的主键(建议使用 id )
     * 将实体类中除主键以外的字段和值分别列出来，适用于主键是自增的表
     *
     * @param entity 实体对象
     * @return insertAndReturnKey的执行结果
     * @throws DuplicateKeyException 当唯一字段重复插入时，会抛出此异常
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insertAndReturnKey")
    Integer baseInsertAndReturnKey(T entity) throws DuplicateKeyException;

    /**
     * 根据 id 删除数据，要求必须要有 id 字段
     *
     * @param entity 实体对象
     * @return deleteById的执行结果
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteById")
    Integer baseDeleteById(T entity);

    /**
     * 根据条件删除数据
     * 传入的对象中带 @IndexAttribute 注解的字段有值的都作为查询条件
     * 多个查询条件用 And 连接
     *
     * @param entity 实体对象
     * @return deleteByCondition的执行结果
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteByCondition")
    Integer baseDeleteByCondition(T entity);

    /**
     * 根据 id 更新数据，空值不更新 ，要求必须有id字段
     *
     * @param entity 实体对象
     * @return updateById的执行结果
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateById")
    Integer baseUpdateById(T entity);

    /**
     * 根据主键更新数据，空值不更新，要求数据至少有一个主键，且主键有值
     *
     * @param entity 实体对象
     * @return updateByKey的执行结果
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateByKey")
    Integer baseUpdateByKey(T entity);

    /**
     * 根据 id 查找数据，要求必须有 id 字段
     *
     * @param entity 实体对象
     * @return selectById的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectById")
    T baseSelectById(T entity);

    /**
     * 根据主键查询数据，要求至少有一个主键，且主键必须有值
     *
     * @param entity 实体对象
     * @return selectByKey的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectByKey")
    T baseSelectByKey(T entity);

    /**
     * 查询全部数据
     *
     * @param entity 实体对象
     * @return selectAll的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectAll")
    List<T> baseSelectAll(T entity);

    /**
     * 根据条件查询数据
     * 传入的对象中带 @IndexAttribute 注解的字段有值的都作为查询条件
     * 传入对象中带 @SortAttribute 注解的字段作为排序字段
     *
     * @param entity 实体对象
     * @return selectByCondition的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectByCondition")
    List<T> baseSelectByCondition(T entity);

    /**
     * 查询记录总数
     *
     * @param entity 实体对象
     * @return selectCount的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectCount")
    Integer baseSelectCount(T entity);

    /**
     * 根据条件查询记录总数
     *
     * @param entity 实体对象
     * @return selectCountByCondition的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectCountByCondition")
    Integer baseSelectCountByCondition(T entity);

    /**
     * 分页查询
     *
     * @param entity 实体对象
     * @return selectPageList的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectPageList")
    List<T> baseSelectPageList(T entity);

    /**
     * 根据条件进行分页查询
     *
     * @param entity 实体对象
     * @return selectPageListByCondition的执行结果
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectPageListByCondition")
    List<T> baseSelectPageListByCondition(T entity);
}
