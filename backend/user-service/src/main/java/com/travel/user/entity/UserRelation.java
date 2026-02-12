package com.travel.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户关系实体类
 */
@Data
@TableName("user_relation")
public class UserRelation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发起用户ID
     */
    private Long fromUserId;

    /**
     * 目标用户ID
     */
    private Long toUserId;

    /**
     * 关系类型：1-关注，2-好友，3-黑名单
     */
    private Integer type;

    /**
     * 状态：0-待确认，1-已确认
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
