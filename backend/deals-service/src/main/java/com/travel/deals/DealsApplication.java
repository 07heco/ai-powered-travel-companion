package com.travel.deals;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.travel.deals.dao")
@ComponentScan(basePackages = {"com.travel.deals", "com.travel.common"})
public class DealsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DealsApplication.class, args);
    }
}
