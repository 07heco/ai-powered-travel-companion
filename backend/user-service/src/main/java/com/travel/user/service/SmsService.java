package com.travel.user.service;

import com.travel.user.config.RabbitMQConfig;
import com.travel.user.entity.SmsMessage;
import com.travel.user.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 短信服务
 */
@Slf4j
@Service
public class SmsService {
    
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    @Resource
    private SmsUtil smsUtil;
    
    /**
     * 发送短信验证码
     * @param phone 手机号
     * @param type 类型（register/login）
     * @return 是否发送成功
     */
    public boolean sendCode(String phone, String type) {
        // 使用Redis分布式锁避免短信重发
        if (!smsUtil.acquireSmsLock(phone, type)) {
            log.warn("短信发送过于频繁: {}", phone);
            return false;
        }
        
        try {
            // 生成验证码
            String code = smsUtil.generateCode();
            
            // 存储验证码到Redis
            smsUtil.storeCode(phone, code, type);
            
            // 创建短信消息
            SmsMessage message = new SmsMessage();
            message.setPhone(phone);
            message.setCode(code);
            message.setType(type);
            message.setTemplateId("SMS_123456789"); // 实际项目中需要替换为真实的模板ID
            
            // 发送消息到RabbitMQ
            rabbitTemplate.convertAndSend(RabbitMQConfig.SMS_EXCHANGE, RabbitMQConfig.SMS_ROUTING_KEY, message);
            
            log.info("短信验证码发送成功: phone={}, code={}", phone, code);
            return true;
        } catch (Exception e) {
            log.error("短信验证码发送失败: {}", e.getMessage(), e);
            return false;
        } finally {
            // 释放锁
            smsUtil.releaseSmsLock(phone, type);
        }
    }
    
    /**
     * 验证短信验证码
     * @param phone 手机号
     * @param code 验证码
     * @param type 类型（register/login）
     * @return 是否验证成功
     */
    public boolean validateCode(String phone, String code, String type) {
        boolean result = smsUtil.validateCode(phone, code, type);
        if (result) {
            // 验证成功后删除验证码，防止重复使用
            smsUtil.deleteCode(phone, type);
        }
        return result;
    }
}
