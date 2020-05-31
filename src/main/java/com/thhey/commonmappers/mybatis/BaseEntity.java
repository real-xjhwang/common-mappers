package com.thhey.commonmappers.mybatis;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础实体类
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
@Getter
@Setter
public class BaseEntity {
    /**
     * 是否是明细字段
     */
    private boolean baseDetailed = true;

    /**
     * 多个查询条件是否使用And连接
     */
    private Boolean baseUseAnd = true;

    /**
     * 是否按排序关键字升序排列
     */
    private Boolean baseUseASC = true;

    /**
     * 每页显示数量
     */
    private int basePageSize = 10;

    /**
     * 当前查询页数
     */
    private int baseCurrentPage = 1;

    /**
     * 查询的起始行号，通过每页显示大小和当前页数计算
     */
    private int baseStartRows;

    /**
     * 设置设置每页显示数量，并更新查询的起始行号
     * @param basePageSize 每页查询数量
     */
    public void setBasePageSize(int basePageSize) {
        this.basePageSize = basePageSize;
        this.baseStartRows = this.basePageSize * (this.baseCurrentPage - 1);
    }

    /**
     * 设置下次查询时的页数，并设置当前起始行号
     * @param baseCurrentPage 下次查询时的页数
     */
    public void setBaseCurrentPage(int baseCurrentPage) {
        this.baseStartRows = this.basePageSize * (this.baseCurrentPage - 1);
        this.baseCurrentPage = baseCurrentPage;
    }
}
