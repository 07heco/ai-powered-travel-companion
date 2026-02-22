package com.travel.ai.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 本地缓存
    private final Map<String, CacheItem> localCache = new ConcurrentHashMap<>();

    // 缓存项
    private static class CacheItem {
        private final Object value;
        private final long expiryTime;

        public CacheItem(Object value, long ttlSeconds) {
            this.value = value;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }

        public Object getValue() {
            return value;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    /**
     * 设置缓存
     * @param key 缓存键
     * @param value 缓存值
     * @param ttlSeconds 过期时间（秒）
     */
    public void set(String key, Object value, long ttlSeconds) {
        // 设置Redis缓存
        redisTemplate.opsForValue().set("ai:" + key, value, ttlSeconds, TimeUnit.SECONDS);
        
        // 设置本地缓存
        localCache.put(key, new CacheItem(value, ttlSeconds));
    }

    /**
     * 获取缓存
     * @param key 缓存键
     * @return 缓存值
     */
    public Object get(String key) {
        // 先从本地缓存获取
        CacheItem localItem = localCache.get(key);
        if (localItem != null && !localItem.isExpired()) {
            return localItem.getValue();
        } else if (localItem != null) {
            // 本地缓存已过期，移除
            localCache.remove(key);
        }

        // 从Redis缓存获取
        Object value = redisTemplate.opsForValue().get("ai:" + key);
        if (value != null) {
            // 更新本地缓存
            localCache.put(key, new CacheItem(value, 600)); // 本地缓存默认10分钟
        }

        return value;
    }

    /**
     * 删除缓存
     * @param key 缓存键
     */
    public void delete(String key) {
        // 删除Redis缓存
        redisTemplate.delete("ai:" + key);
        // 删除本地缓存
        localCache.remove(key);
    }

    /**
     * 清除所有缓存
     */
    public void clear() {
        // 清除Redis缓存
        // 注意：这里应该使用更精确的方式，避免影响其他服务的缓存
        // 这里简化处理，实际项目中应该使用Redis的SCAN命令
        
        // 清除本地缓存
        localCache.clear();
    }

    /**
     * 检查缓存是否存在
     * @param key 缓存键
     * @return 是否存在
     */
    public boolean exists(String key) {
        // 检查本地缓存
        CacheItem localItem = localCache.get(key);
        if (localItem != null && !localItem.isExpired()) {
            return true;
        } else if (localItem != null) {
            // 本地缓存已过期，移除
            localCache.remove(key);
        }

        // 检查Redis缓存
        return redisTemplate.hasKey("ai:" + key);
    }
}