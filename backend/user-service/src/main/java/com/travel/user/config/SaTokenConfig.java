package com.travel.user.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.dev33.satoken.router.SaRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    
    /**
     * 注册Sa-Token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token拦截器，排除不需要认证的路径
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 登录认证：除了登录注册接口，其他都需要认证
            SaRouter.match("/api/**", "/user/api/**", r -> {
                // 排除登录注册相关接口
                SaRouter.notMatch("/api/auth/login", "/api/auth/register", "/api/auth/send-code", "/api/auth/wechat/login", "/api/auth/wechat/callback");
                // 其他接口需要登录认证
                StpUtil.checkLogin();
            });
        })).addPathPatterns("/**");
    }
    
    // 密码加密使用Spring Security的PasswordEncoder
    // SaSecureUtil的构造函数是private的，无法通过Bean注入
    
    /**
     * Sa-Token异常处理类
     */
    @RestControllerAdvice
    public static class SaTokenExceptionHandler {
        
        @ExceptionHandler(Exception.class)
        public SaResult handleException(Exception e) {
            // 处理登录异常
            if (e instanceof NotLoginException) {
                return SaResult.error("未登录").setCode(401);
            }
            // 处理权限异常
            if (e instanceof NotPermissionException) {
                return SaResult.error("无权限").setCode(403);
            }
            // 其他异常
            return SaResult.error(e.getMessage()).setCode(500);
        }
    }
}
