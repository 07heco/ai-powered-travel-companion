package com.travel.destination.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 景点图片实体类
 */
@Data
@TableName("attraction_image")
public class AttractionImage {
    /**
     * 图片ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 景点ID
     */
    @TableField(value = "attraction_id")
    private Long attractionId;

    /**
     * 图片URL
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 排序
     */
    private Integer sort;

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
