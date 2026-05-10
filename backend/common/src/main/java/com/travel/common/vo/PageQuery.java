package com.travel.common.vo;

import lombok.Data;

/**
 * 分页查询参数
 */
@Data
public class PageQuery {
    /**
     * 页码
     */
    private Integer page = 1;

    /**
     * 每页大小
     */
    private Integer size = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方式：asc或desc
     */
    private String sortOrder;
}
