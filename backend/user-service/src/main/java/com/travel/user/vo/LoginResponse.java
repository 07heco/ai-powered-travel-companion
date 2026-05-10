package com.travel.user.vo;

import lombok.Data;

/**
 * 登录响应
 */
@Data
public class LoginResponse {
    /**
     * 访问令牌
     */
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
    
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
    
    /**
     * 头像
     */
    private String avatar;
}
