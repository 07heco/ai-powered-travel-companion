package com.travel.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.user.entity.User;
import com.travel.user.vo.*;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 用户注册
     */
    RegisterResponse register(RegisterRequest request);
    
    /**
     * 发送验证码
     */
    boolean sendCode(String phone, String type);
    
    /**
     * 获取用户信息
     */
    UserInfoResponse getUserInfo(Long userId);
    
    /**
     * 更新用户信息
     */
    boolean updateUserInfo(Long userId, UpdateUserInfoRequest request);
    
    /**
     * 修改密码
     */
    boolean changePassword(Long userId, ChangePasswordRequest request);
    
    /**
     * 登出
     */
    boolean logout(String token);
}
