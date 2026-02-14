package com.travel.booking.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class DistributedLockUtil {
    @Resource
    private RedissonClient redissonClient;
    
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        try {
            if (redissonClient != null) {
                RLock lock = redissonClient.getLock(lockKey);
                return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            } else {
                // Redis不可用时，直接返回true，跳过分布式锁检查
                return true;
            }
        } catch (Exception e) {
            // Redis连接失败时，直接返回true，跳过分布式锁检查
            return true;
        }
    }
    
    public void unlock(String lockKey) {
        try {
            if (redissonClient != null) {
                RLock lock = redissonClient.getLock(lockKey);
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            // Redis连接失败时，忽略解锁操作
        }
    }
}
