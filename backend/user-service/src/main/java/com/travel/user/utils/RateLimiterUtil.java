package com.travel.user.utils;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 限流工具类
 */
@Slf4j
@Component
public class RateLimiterUtil {

    /**
     * 存储不同接口的限流器
     */
    private static final Map<String, RateLimiter> RATE_LIMITER_MAP = new ConcurrentHashMap<>();

    /**
     * 存储不同IP的限流器
     */
    private static final Map<String, RateLimiter> IP_RATE_LIMITER_MAP = new ConcurrentHashMap<>();

    /**
     * 获取接口限流器
     * @param key 限流键
     * @param qps 每秒请求数
     * @return 限流器
     */
    public RateLimiter getRateLimiter(String key, double qps) {
        return RATE_LIMITER_MAP.computeIfAbsent(key, k -> RateLimiter.create(qps));
    }

    /**
     * 获取IP限流器
     * @param ip IP地址
     * @param qps 每秒请求数
     * @return 限流器
     */
    public RateLimiter getIpRateLimiter(String ip, double qps) {
        return IP_RATE_LIMITER_MAP.computeIfAbsent(ip, k -> RateLimiter.create(qps));
    }

    /**
     * 尝试获取令牌（接口级别限流）
     * @param key 限流键
     * @param qps 每秒请求数
     * @return 是否获取成功
     */
    public boolean tryAcquire(String key, double qps) {
        RateLimiter rateLimiter = getRateLimiter(key, qps);
        boolean acquired = rateLimiter.tryAcquire();
        if (!acquired) {
            log.warn("接口限流: {}", key);
        }
        return acquired;
    }

    /**
     * 尝试获取令牌（IP级别限流）
     * @param request HttpServletRequest
     * @param qps 每秒请求数
     * @return 是否获取成功
     */
    public boolean tryAcquireByIp(HttpServletRequest request, double qps) {
        String ip = getClientIp(request);
        RateLimiter rateLimiter = getIpRateLimiter(ip, qps);
        boolean acquired = rateLimiter.tryAcquire();
        if (!acquired) {
            log.warn("IP限流: {}", ip);
        }
        return acquired;
    }

    /**
     * 获取客户端IP地址
     * @param request HttpServletRequest
     * @return IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
