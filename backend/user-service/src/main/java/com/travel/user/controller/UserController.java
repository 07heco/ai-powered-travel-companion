package com.travel.user.controller;

import com.travel.common.vo.R;
import com.travel.user.service.UserService;
import com.travel.user.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "用户认证服务", description = "提供登录、注册、验证码、用户信息与登出相关接口")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "使用手机号和密码登录，成功后返回 accessToken、refreshToken 和用户基础信息")
    @PostMapping("/login")
    public R<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return R.success(response);
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "使用手机号、验证码和密码注册新用户")
    @PostMapping("/register")
    public R<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = userService.register(request);
            return R.success(response);
        } catch (Exception e) {
            log.error("注册失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 发送验证码
     */
    @Operation(summary = "发送验证码", description = "向指定手机号发送指定业务类型的验证码，当前实现为模拟发送并写入日志")
    @PostMapping("/send-code")
    public R<Boolean> sendCode(@RequestParam String phone, @RequestParam String type) {
        try {
            boolean result = userService.sendCode(phone, type);
            return R.success(result);
        } catch (Exception e) {
            log.error("发送验证码失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息", description = "从请求头 userId 中读取用户ID并返回用户基础信息")
    @GetMapping("/user-info")
    public R<UserInfoResponse> getUserInfo(HttpServletRequest request) {
        try {
            // 从请求头中获取用户ID
            String userIdStr = request.getHeader("userId");
            if (userIdStr == null || userIdStr.isBlank()) {
                return R.unauth();
            }
            Long userId = Long.parseLong(userIdStr);
            UserInfoResponse response = userService.getUserInfo(userId);
            return R.success(response);
        } catch (Exception e) {
            log.error("获取用户信息失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息", description = "从请求头 userId 中读取用户ID并更新昵称、头像、城市、简介等资料")
    @PutMapping("/update-info")
    public R<Boolean> updateUserInfo(HttpServletRequest request, @RequestBody UpdateUserInfoRequest updateRequest) {
        try {
            // 从请求头中获取用户ID
            String userIdStr = request.getHeader("userId");
            if (userIdStr == null || userIdStr.isBlank()) {
                return R.unauth();
            }
            Long userId = Long.parseLong(userIdStr);
            boolean result = userService.updateUserInfo(userId, updateRequest);
            return R.success(result);
        } catch (Exception e) {
            log.error("更新用户信息失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @Operation(summary = "修改密码", description = "从请求头 userId 中读取用户ID并修改账户密码")
    @PutMapping("/change-password")
    public R<Boolean> changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest passwordRequest) {
        try {
            // 从请求头中获取用户ID
            String userIdStr = request.getHeader("userId");
            if (userIdStr == null || userIdStr.isBlank()) {
                return R.unauth();
            }
            Long userId = Long.parseLong(userIdStr);
            boolean result = userService.changePassword(userId, passwordRequest);
            return R.success(result);
        } catch (Exception e) {
            log.error("修改密码失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 登出
     */
    @Operation(summary = "用户登出", description = "从 Authorization 请求头中读取 Bearer Token 并执行登出")
    @PostMapping("/logout")
    public R<Boolean> logout(HttpServletRequest request) {
        try {
            // 从请求头中获取token
            String token = request.getHeader("Authorization");
            if (token == null) {
                return R.unauth();
            }
            // 移除Bearer前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            boolean result = userService.logout(token);
            return R.success(result);
        } catch (Exception e) {
            log.error("登出失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
}
