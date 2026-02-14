package com.travel.user.consumer;

import com.travel.user.config.RabbitMQConfig;
import com.travel.user.entity.SmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 短信消费者
 */
@Slf4j
@Component
public class SmsConsumer {
    
    /**
     * 监听短信队列，接收并处理短信消息
     */
    @RabbitListener(queues = RabbitMQConfig.SMS_QUEUE)
    public void handleSmsMessage(SmsMessage message) {
        try {
            log.info("接收短信消息: phone={}, code={}, type={}", message.getPhone(), message.getCode(), message.getType());
            
            // 调用短信发送API发送短信
            // 这里是模拟发送，实际项目中需要集成真实的短信服务提供商
            sendSms(message);
            
            log.info("短信发送成功: phone={}", message.getPhone());
        } catch (Exception e) {
            log.error("短信发送失败: {}", e.getMessage(), e);
            // 可以添加重试机制或死信队列处理
        }
    }
    
    /**
     * 发送短信
     * 实际项目中需要集成真实的短信服务提供商
     */
    private void sendSms(SmsMessage message) {
        // 模拟短信发送
        // 实际实现示例（以阿里云短信服务为例）：
        /*
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "accessKeyId", "accessKeySecret");
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(message.getPhone());
        request.setSignName("旅行助手");
        request.setTemplateCode(message.getTemplateId());
        request.setTemplateParam("{\"code\":\"" + message.getCode() + "\"}");
        SendSmsResponse response = client.getAcsResponse(request);
        */
        
        // 模拟网络延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        log.info("模拟发送短信到: {}", message.getPhone());
        log.info("短信内容: 您的{}验证码是: {}, 5分钟内有效", 
                "register".equals(message.getType()) ? "注册" : "登录", 
                message.getCode());
    }
}
