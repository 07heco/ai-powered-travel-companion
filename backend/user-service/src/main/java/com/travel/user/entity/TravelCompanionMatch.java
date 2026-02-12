package com.travel.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 旅伴匹配实体类
 */
@Data
@TableName("travel_companion_match")
public class TravelCompanionMatch implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 匹配用户ID
     */
    private Long matchUserId;

    /**
     * 匹配分数
     */
    private Integer matchScore;

    /**
     * 匹配原因
     */
    private String matchReason;

    /**
     * 状态：0-待确认，1-已确认，2-已拒绝
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
}
