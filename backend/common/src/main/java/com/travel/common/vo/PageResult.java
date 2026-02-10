package com.travel.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页响应对象
 */
@Data
public class PageResult<T> {
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 每页大小
     */
    private Integer size;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 构造方法
     */
    public PageResult(Long total, Integer size, Integer pages, Integer current, List<T> records) {
        this.total = total;
        this.size = size;
        this.pages = pages;
        this.current = current;
        this.records = records;
    }

    /**
     * 构造方法
     */
    public PageResult(Long total, Integer size, Integer current, List<T> records) {
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = (int) Math.ceil((double) total / size);
        this.records = records;
    }
}
