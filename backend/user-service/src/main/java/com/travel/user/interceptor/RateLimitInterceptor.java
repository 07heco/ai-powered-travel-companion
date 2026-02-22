package com.travel.user.interceptor;

import com.travel.common.vo.R;
import com.travel.user.annotation.RateLimit;
import com.travel.user.utils.RateLimiterUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 限流拦截器
 */
@Slf4j
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Resource
    private RateLimiterUtil rateLimiterUtil;

    @Resource
    private ObjectMapper objectMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否是方法处理器
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取方法和注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RateLimit rateLimitAnnotation = method.getAnnotation(RateLimit.class);

        // 如果没有限流注解，直接通过
        if (rateLimitAnnotation == null) {
            return true;
        }

        // 获取限流参数
        double qps = rateLimitAnnotation.qps();
        RateLimit.Type type = rateLimitAnnotation.type();

        // 执行限流逻辑
        boolean allowed;
        if (RateLimit.Type.IP.equals(type)) {
            // IP级别限流
            allowed = rateLimiterUtil.tryAcquireByIp(request, qps);
        } else {
            // 接口级别限流
            String key = method.getDeclaringClass().getName() + "." + method.getName();
            allowed = rateLimiterUtil.tryAcquire(key, qps);
        }

        // 如果限流，返回错误响应
        if (!allowed) {
            response.setStatus(429); // SC_TOO_MANY_REQUESTS
            response.setContentType("application/json; charset=utf-8");
            R<?> errorResponse = R.error("请求过于频繁，请稍后重试");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return false;
        }

        return true;
    }
}
