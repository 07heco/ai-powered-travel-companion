package com.travel.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.travel.common.vo.R;
import com.travel.user.service.UserService;
import com.travel.user.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 用户控制器
 */
@Tag(name = "用户接口", description = "处理用户信息管理相关操作")
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息", description = "获取当前登录用户的详细信息")
    @GetMapping("/info")
    public R<UserInfoResponse> getUserInfo() {
        try {
            // 使用Sa-Token获取用户ID
            Long userId = StpUtil.getLoginIdAsLong();
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
    @Operation(summary = "更新用户信息", description = "更新当前登录用户的信息")
    @PutMapping("/info")
    public R<Boolean> updateUserInfo(@Parameter(description = "更新用户信息请求参数", required = true) @RequestBody UpdateUserInfoRequest updateRequest) {
        try {
            // 使用Sa-Token获取用户ID
            Long userId = StpUtil.getLoginIdAsLong();
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
    @Operation(summary = "修改密码", description = "修改当前登录用户的密码")
    @PutMapping("/password")
    public R<Boolean> changePassword(@Parameter(description = "修改密码请求参数", required = true) @RequestBody ChangePasswordRequest passwordRequest) {
        try {
            // 使用Sa-Token获取用户ID
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = userService.changePassword(userId, passwordRequest);
            return R.success(result);
        } catch (Exception e) {
            log.error("修改密码失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
}
