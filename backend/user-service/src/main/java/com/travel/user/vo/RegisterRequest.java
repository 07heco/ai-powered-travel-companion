package com.travel.user.vo;

import lombok.Data;

/**
 * 注册请求
 */
@Data
public class RegisterRequest {
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 验证码
     */
    private String code;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 确认密码
     */
    private String confirmPassword;
}
