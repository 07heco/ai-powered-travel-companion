package com.travel.user.controller;

import com.travel.common.vo.R;
import com.travel.user.service.AuthService;
import com.travel.user.service.SmsService;
import com.travel.user.vo.LoginRequest;
import com.travel.user.vo.LoginResponse;
import com.travel.user.vo.RegisterRequest;
import com.travel.user.vo.RegisterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Resource
    private AuthService authService;
    
    @Resource
    private SmsService smsService;
    
    /**
     * 微信登录
     * @param code 微信授权code
     * @return 登录响应
     */
    @PostMapping("/wechat/login")
    public R<LoginResponse> wechatLogin(@RequestParam String code) {
        try {
            LoginResponse response = authService.wechatLogin(code);
            return R.success(response);
        } catch (Exception e) {
            log.error("微信登录失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 绑定微信账号
     * @param code 微信授权code
     * @param request HttpServletRequest
     * @return 是否绑定成功
     */
    @PostMapping("/wechat/bind")
    public R<Boolean> bindWechat(@RequestParam String code, HttpServletRequest request) {
        try {
            // 从请求中获取用户ID
            Long userId = getUserIdFromRequest(request);
            boolean result = authService.bindWechat(userId, code);
            return R.success(result);
        } catch (Exception e) {
            log.error("绑定微信失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 登出
     * @return 是否登出成功
     */
    @PostMapping("/logout")
    public R<Boolean> logout() {
        try {
            authService.logout();
            return R.success(true);
        } catch (Exception e) {
            log.error("登出失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 手机号登录
     * @param request 登录请求
     * @return 登录响应
     */
    @PostMapping("/login")
    public R<LoginResponse> phoneLogin(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.phoneLogin(request);
            return R.success(response);
        } catch (Exception e) {
            log.error("手机号登录失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 手机号注册
     * @param request 注册请求
     * @return 注册响应
     */
    @PostMapping("/register")
    public R<RegisterResponse> phoneRegister(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = authService.phoneRegister(request);
            return R.success(response);
        } catch (Exception e) {
            log.error("手机号注册失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 发送验证码
     * @param phone 手机号
     * @param type 类型（register/login）
     * @return 是否发送成功
     */
    @PostMapping("/send-code")
    public R<Boolean> sendCode(@RequestParam String phone, @RequestParam String type) {
        try {
            boolean result = smsService.sendCode(phone, type);
            return R.success(result);
        } catch (Exception e) {
            log.error("发送验证码失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 从请求中获取用户ID
     * @param request HttpServletRequest
     * @return 用户ID
     */
    private Long getUserIdFromRequest(HttpServletRequest request) {
        // 从请求头中获取用户ID
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            throw new RuntimeException("用户未登录");
        }
        return Long.parseLong(userIdStr);
    }
}
