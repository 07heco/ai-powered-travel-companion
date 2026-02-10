package com.travel.user.controller;

import com.travel.common.vo.R;
import com.travel.user.service.UserService;
import com.travel.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     */
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
    @GetMapping("/user-info")
    public R<UserInfoResponse> getUserInfo(HttpServletRequest request) {
        try {
            // 从请求头中获取用户ID
            String userIdStr = request.getHeader("userId");
            if (userIdStr == null) {
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
    @PutMapping("/update-info")
    public R<Boolean> updateUserInfo(HttpServletRequest request, @RequestBody UpdateUserInfoRequest updateRequest) {
        try {
            // 从请求头中获取用户ID
            String userIdStr = request.getHeader("userId");
            if (userIdStr == null) {
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
    @PutMapping("/change-password")
    public R<Boolean> changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest passwordRequest) {
        try {
            // 从请求头中获取用户ID
            String userIdStr = request.getHeader("userId");
            if (userIdStr == null) {
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
