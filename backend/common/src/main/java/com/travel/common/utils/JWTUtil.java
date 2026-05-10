package com.travel.common.utils;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
@Slf4j
public class JWTUtil {

    /**
     * 密钥
     */
    private static final String SECRET_KEY = "travel_companion_secret_key_2026";

    /**
     * 过期时间（小时）
     */
    private static final int EXPIRE_HOURS = 24;

    /**
     * 生成token
     */
    public static String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expireDate = DateUtil.offsetHour(now, EXPIRE_HOURS);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 生成token
     */
    public static String generateToken(String subject) {
        return generateToken(subject, null);
    }

    /**
     * 解析token
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("解析token失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取subject
     */
    public static String getSubject(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 获取claim
     */
    public static Object getClaim(String token, String key) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get(key) : null;
    }

    /**
     * 验证token是否过期
     */
    public static boolean isExpired(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return true;
        }
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 验证token是否有效
     */
    public static boolean isValid(String token) {
        return parseToken(token) != null && !isExpired(token);
    }
}
