package com.travel.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.travel.common.vo.R;
import com.travel.user.annotation.RateLimit;
import com.travel.user.service.AuthService;
import com.travel.user.service.SmsService;
import com.travel.user.vo.LoginRequest;
import com.travel.user.vo.LoginResponse;
import com.travel.user.vo.RegisterRequest;
import com.travel.user.vo.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 认证控制器
 */
@Tag(name = "认证接口", description = "处理用户登录、注册、验证码等认证相关操作")
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
    @Operation(summary = "微信登录", description = "通过微信授权code进行登录，返回登录凭证和用户信息")
    @PostMapping("/wechat/login")
    @RateLimit(qps = 5.0, type = RateLimit.Type.IP)
    public R<LoginResponse> wechatLogin(@Parameter(description = "微信授权code", required = true) @RequestParam String code) {
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
     * @return 是否绑定成功
     */
    @Operation(summary = "绑定微信账号", description = "将微信账号绑定到当前登录的用户")
    @PostMapping("/wechat/bind")
    @RateLimit(qps = 3.0, type = RateLimit.Type.IP)
    public R<Boolean> bindWechat(@Parameter(description = "微信授权code", required = true) @RequestParam String code) {
        try {
            // 使用Sa-Token获取用户ID
            Long userId = StpUtil.getLoginIdAsLong();
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
    @Operation(summary = "登出", description = "用户登出，清除登录状态")
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
    @Operation(summary = "手机号登录", description = "通过手机号和密码或验证码进行登录，返回登录凭证和用户信息")
    @PostMapping("/login")
    @RateLimit(qps = 5.0, type = RateLimit.Type.IP)
    public R<LoginResponse> phoneLogin(@Parameter(description = "登录请求参数", required = true) @RequestBody LoginRequest request) {
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
    @Operation(summary = "手机号注册", description = "通过手机号和验证码进行注册，返回用户信息")
    @PostMapping("/register")
    @RateLimit(qps = 3.0, type = RateLimit.Type.IP)
    public R<RegisterResponse> phoneRegister(@Parameter(description = "注册请求参数", required = true) @RequestBody RegisterRequest request) {
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
    @Operation(summary = "发送验证码", description = "向指定手机号发送验证码，用于注册或登录")
    @PostMapping("/send-code")
    @RateLimit(qps = 2.0, type = RateLimit.Type.IP)
    public R<Boolean> sendCode(
            @Parameter(description = "手机号", required = true) @RequestParam String phone,
            @Parameter(description = "验证码类型（register/login）", required = true) @RequestParam String type) {
        try {
            boolean result = smsService.sendCode(phone, type);
            return R.success(result);
        } catch (Exception e) {
            log.error("发送验证码失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
}
