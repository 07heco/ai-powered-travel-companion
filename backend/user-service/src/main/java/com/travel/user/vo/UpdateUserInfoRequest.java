package com.travel.user.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 更新用户信息请求
 */
@Data
public class UpdateUserInfoRequest {
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;
    
    /**
     * 生日
     */
    private LocalDateTime birthday;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 城市
     */
    private String city;
    
    /**
     * 个人简介
     */
    private String bio;
}
