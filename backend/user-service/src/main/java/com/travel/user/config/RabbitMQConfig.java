package com.travel.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {
    
    /**
     * 短信交换机名称
     */
    public static final String SMS_EXCHANGE = "sms-exchange";
    
    /**
     * 短信队列名称
     */
    public static final String SMS_QUEUE = "sms-queue";
    
    /**
     * 短信路由键
     */
    public static final String SMS_ROUTING_KEY = "sms.send";
    
    /**
     * 创建短信交换机
     */
    @Bean
    public DirectExchange smsExchange() {
        return new DirectExchange(SMS_EXCHANGE, true, false);
    }
    
    /**
     * 创建短信队列
     */
    @Bean
    public Queue smsQueue() {
        return new Queue(SMS_QUEUE, true, false, false);
    }
    
    /**
     * 绑定短信队列到交换机
     */
    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue()).to(smsExchange()).with(SMS_ROUTING_KEY);
    }
}
