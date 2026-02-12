package com.travel.user.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 短信消息实体类
 */
@Data
public class SmsMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 类型（register/login）
     */
    private String type;

    /**
     * 模板ID
     */
    private String templateId;
}
