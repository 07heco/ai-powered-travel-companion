package com.travel.user.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.travel.user.constant.RedisKeyConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.UUID;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 短信工具类
 */
@Component
public class SmsUtil {
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 生成6位数字验证码
     */
    public String generateCode() {
        return RandomUtil.randomNumbers(6);
    }
    
    /**
     * 存储验证码到Redis
     * @param phone 手机号
     * @param code 验证码
     * @param type 类型（register/login）
     */
    public void storeCode(String phone, String code, String type) {
        String key = RedisKeyConstant.getSmsCodeKey(phone, type);
        // 存储验证码，包含验证码和使用次数
        redisTemplate.opsForValue().set(key, code + ":0", 5, TimeUnit.MINUTES);
    }
    
    /**
     * 验证验证码
     * @param phone 手机号
     * @param code 验证码
     * @param type 类型（register/login）
     * @return 是否验证成功
     */
    public boolean validateCode(String phone, String code, String type) {
        String key = RedisKeyConstant.getSmsCodeKey(phone, type);
        String storedValue = (String) redisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(storedValue)) {
            return false;
        }
        
        // 解析存储的值，格式为 "code:useCount"
        String[] parts = storedValue.split(":");
        if (parts.length != 2) {
            return false;
        }
        
        String storedCode = parts[0];
        int useCount = Integer.parseInt(parts[1]);
        
        // 验证验证码是否正确且未被使用
        if (!storedCode.equals(code) || useCount >= 1) {
            return false;
        }
        
        // 更新使用次数
        redisTemplate.opsForValue().set(key, storedCode + ":1", 5, TimeUnit.MINUTES);
        return true;
    }
    
    /**
     * 删除验证码
     * @param phone 手机号
     * @param type 类型（register/login）
     */
    public void deleteCode(String phone, String type) {
        String key = RedisKeyConstant.getSmsCodeKey(phone, type);
        redisTemplate.delete(key);
    }
    
    /**
     * 使用Redis分布式锁避免短信重发
     * @param phone 手机号
     * @param type 类型（register/login）
     * @return 是否获取到锁
     */
    public boolean acquireSmsLock(String phone, String type) {
        String lockKey = RedisKeyConstant.getSmsLockKey(phone, type);
        String requestId = UUID.randomUUID().toString();
        
        // 使用Lua脚本保证原子性
        String luaScript = "" +
                "if redis.call('exists', KEYS[1]) == 0 then " +
                "    redis.call('set', KEYS[1], ARGV[1], 'EX', 60) " +
                "    return 1 " +
                "else " +
                "    return 0 " +
                "end ";
        
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        
        return result != null && result == 1;
    }
    
    /**
     * 释放短信发送锁
     * @param phone 手机号
     * @param type 类型（register/login）
     */
    public void releaseSmsLock(String phone, String type) {
        String lockKey = RedisKeyConstant.getSmsLockKey(phone, type);
        redisTemplate.delete(lockKey);
    }
}
