package com.travel.user.vo;

import lombok.Data;

/**
 * 登录请求
 */
@Data
public class LoginRequest {
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 验证码（可选）
     */
    private String code;
}
