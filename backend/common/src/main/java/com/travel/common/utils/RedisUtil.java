package com.travel.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Slf4j
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置键值对
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis设置失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 设置键值对（带过期时间）
     */
    public boolean set(String key, Object value, long time, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, time, unit);
            return true;
        } catch (Exception e) {
            log.error("Redis设置失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取值
     */
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis获取失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 删除键
     */
    public boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis删除失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 批量删除键
     */
    public boolean delete(Collection<String> keys) {
        try {
            redisTemplate.delete(keys);
            return true;
        } catch (Exception e) {
            log.error("Redis批量删除失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 键是否存在
     */
    public boolean exists(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("Redis检查失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 设置过期时间
     */
    public boolean expire(String key, long time, TimeUnit unit) {
        try {
            return Boolean.TRUE.equals(redisTemplate.expire(key, time, unit));
        } catch (Exception e) {
            log.error("Redis设置过期时间失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取过期时间
     */
    public Long getExpire(String key) {
        try {
            return redisTemplate.getExpire(key);
        } catch (Exception e) {
            log.error("Redis获取过期时间失败: {}", e.getMessage());
            return -1L;
        }
    }

    /**
     * 自增
     */
    public Long increment(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("Redis自增失败: {}", e.getMessage());
            return 0L;
        }
    }

    /**
     * 自减
     */
    public Long decrement(String key, long delta) {
        try {
            return redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            log.error("Redis自减失败: {}", e.getMessage());
            return 0L;
        }
    }

    /**
     * 添加到集合
     */
    public boolean addToSet(String key, Object... values) {
        try {
            redisTemplate.opsForSet().add(key, values);
            return true;
        } catch (Exception e) {
            log.error("Redis添加到集合失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从集合中移除
     */
    public boolean removeFromSet(String key, Object... values) {
        try {
            redisTemplate.opsForSet().remove(key, values);
            return true;
        } catch (Exception e) {
            log.error("Redis从集合中移除失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取集合成员
     */
    public Set<Object> getSetMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("Redis获取集合成员失败: {}", e.getMessage());
            return Collections.emptySet();
        }
    }

    /**
     * 添加到列表
     */
    public boolean addToList(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis添加到列表失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取列表范围
     */
    public List<Object> getListRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis获取列表范围失败: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 设置哈希字段
     */
    public boolean setHashField(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            log.error("Redis设置哈希字段失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取哈希字段
     */
    public Object getHashField(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            log.error("Redis获取哈希字段失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取哈希所有字段
     */
    public Map<Object, Object> getHashAll(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("Redis获取哈希所有字段失败: {}", e.getMessage());
            return Collections.emptyMap();
        }
    }
}
