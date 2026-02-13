package com.travel.booking.utils;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WorkerIdHolder implements CommandLineRunner {

    @Autowired
    private RedissonClient redissonClient;

    public static long WORKER_ID;

    @Override
    public void run(String... args) throws Exception {
        WORKER_ID = redissonClient.getAtomicLong("workerId").incrementAndGet() % 32;
    }
}
