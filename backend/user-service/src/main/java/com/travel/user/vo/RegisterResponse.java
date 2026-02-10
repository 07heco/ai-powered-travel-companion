package com.travel.user.vo;

import lombok.Data;

/**
 * 注册响应
 */
@Data
public class RegisterResponse {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 昵称
     */
    private String nickname;
}
