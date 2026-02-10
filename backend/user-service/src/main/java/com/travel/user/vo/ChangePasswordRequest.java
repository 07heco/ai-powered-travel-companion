package com.travel.user.vo;

import lombok.Data;

/**
 * 修改密码请求
 */
@Data
public class ChangePasswordRequest {
    /**
     * 旧密码
     */
    private String oldPassword;
    
    /**
     * 新密码
     */
    private String newPassword;
    
    /**
     * 确认新密码
     */
    private String confirmPassword;
}
