package com.travel.booking.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RedissonConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);
    
    @Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private String port;
    
    @Value("${spring.redis.password}")
    private String password;
    
    @Bean
    public RedissonClient redissonClient() {
        try {
            Config config = new Config();
            config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password)
                .setDatabase(0);
            return Redisson.create(config);
        } catch (Exception e) {
            logger.error("Redis连接失败，将在Redis可用时自动重连: {}", e.getMessage());
            throw e;
        }
    }
}
