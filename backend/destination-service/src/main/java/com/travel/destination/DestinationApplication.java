package com.travel.destination;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.travel.destination.dao")
@ComponentScan(basePackages = {"com.travel.destination", "com.travel.common"})
public class DestinationApplication {
    public static void main(String[] args) {
        SpringApplication.run(DestinationApplication.class, args);
    }
}
