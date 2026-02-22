package com.travel.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.user.dao.UserDao;
import com.travel.user.entity.User;
import com.travel.user.service.AuthService;
import com.travel.user.service.SmsService;
import com.travel.user.service.UserService;
import com.travel.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private AuthService authService;

    @Resource
    private SmsService smsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 用户登录
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        // 使用AuthService处理登录
        return authService.phoneLogin(request);
    }

    /**
     * 用户注册
     */
    @Override
    public RegisterResponse register(RegisterRequest request) {
        // 使用AuthService处理注册
        return authService.phoneRegister(request);
    }

    /**
     * 发送验证码
     */
    @Override
    public boolean sendCode(String phone, String type) {
        // 使用SmsService发送验证码
        return smsService.sendCode(phone, type);
    }

    /**
     * 获取用户信息
     */
    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        // 查询用户
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 构建响应
        UserInfoResponse response = new UserInfoResponse();
        response.setId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        response.setGender(user.getGender());
        response.setBirthday(user.getBirthday());
        response.setEmail(user.getEmail());
        response.setCity(user.getCity());
        response.setBio(user.getBio());
        response.setType(user.getType());
        response.setCreateTime(user.getCreateTime());

        return response;
    }

    /**
     * 更新用户信息
     */
    @Override
    public boolean updateUserInfo(Long userId, UpdateUserInfoRequest request) {
        // 查询用户
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 更新用户信息
        if (StrUtil.isNotBlank(request.getNickname())) {
            user.setNickname(request.getNickname());
        }
        if (StrUtil.isNotBlank(request.getAvatar())) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getBirthday() != null) {
            user.setBirthday(request.getBirthday());
        }
        if (StrUtil.isNotBlank(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (StrUtil.isNotBlank(request.getCity())) {
            user.setCity(request.getCity());
        }
        if (StrUtil.isNotBlank(request.getBio())) {
            user.setBio(request.getBio());
        }

        user.setUpdateTime(LocalDateTime.now());

        // 保存更新
        return updateById(user);
    }

    /**
     * 修改密码
     */
    @Override
    public boolean changePassword(Long userId, ChangePasswordRequest request) {
        // 验证旧密码
        if (StrUtil.isBlank(request.getOldPassword())) {
            throw new RuntimeException("旧密码不能为空");
        }

        // 验证新密码
        if (StrUtil.isBlank(request.getNewPassword())) {
            throw new RuntimeException("新密码不能为空");
        }

        // 验证确认密码
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 验证密码强度
        if (!com.travel.user.utils.PasswordUtil.validatePassword(request.getNewPassword())) {
            throw new RuntimeException(com.travel.user.utils.PasswordUtil.getPasswordStrengthTip(request.getNewPassword()));
        }

        // 查询用户
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("旧密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdateTime(LocalDateTime.now());

        // 保存更新
        return updateById(user);
    }

    /**
     * 登出
     */
    @Override
    public boolean logout(String token) {
        // 使用AuthService处理登出
        authService.logout();
        return true;
    }
}
