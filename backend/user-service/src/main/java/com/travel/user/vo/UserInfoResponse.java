package com.travel.user.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息响应
 */
@Data
public class UserInfoResponse {
    /**
     * 用户ID
     */
    private Long id;
    
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
    
    /**
     * 用户类型：0-普通用户，1-旅行达人，2-商家
     */
    private Integer type;
    
    /**
     * 注册时间
     */
    private LocalDateTime createTime;
}
