package com.travel.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 第三方账号关联实体类
 */
@Data
@TableName("user_third_party")
public class UserThirdParty implements Serializable {
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
     * 第三方类型（wechat等）
     */
    private String thirdType;

    /**
     * 第三方OpenID
     */
    private String thirdOpenid;

    /**
     * 第三方UnionID
     */
    private String thirdUnionid;

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
}
