package com.travel.destination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 景点实体类
 */
@Data
@TableName("attraction")
public class Attraction {
    /**
     * 景点ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 目的地ID
     */
    @TableField(value = "destination_id")
    private Long destinationId;

    /**
     * 景点名称
     */
    private String name;

    /**
     * 景点描述
     */
    private String description;

    /**
     * 景点地址
     */
    private String address;

    /**
     * 景点图片
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 评分
     */
    private Double rating;

    /**
     * 景点分类
     */
    private String category;

    /**
     * 开放时间
     */
    @TableField(value = "opening_hours")
    private String openingHours;

    /**
     * 门票价格
     */
    @TableField(value = "ticket_price")
    private Double ticketPrice;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
