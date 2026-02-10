package com.travel.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.message.dao.MessageDao;
import com.travel.message.entity.Message;
import com.travel.message.service.MessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(Long userId, String title, String content, String messageType) {
        Message message = new Message();
        message.setUserId(userId);
        message.setTitle(title);
        message.setContent(content);
        message.setMessageType(messageType);
        message.setStatus("UNREAD");
        message.setSentAt(LocalDateTime.now());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        messageDao.insert(message);

        rabbitTemplate.convertAndSend("message-exchange", "message.routing.key", message);
    }

    @Override
    public PageResult<Message> getUserMessages(Long userId, PageQuery query) {
        Page<Message> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getUserId, userId);
        wrapper.orderByDesc(Message::getSentAt);
        Page<Message> result = messageDao.selectPage(page, wrapper);
        return new PageResult<Message>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public Message getMessageById(Long id) {
        return messageDao.selectById(id);
    }

    @Override
    public void markMessageAsRead(Long messageId) {
        Message message = messageDao.selectById(messageId);
        if (message != null) {
            message.setStatus("READ");
            message.setUpdatedAt(LocalDateTime.now());
            messageDao.updateById(message);
        }
    }

    @Override
    public void deleteMessage(Long messageId) {
        messageDao.deleteById(messageId);
    }

    @Override
    public Integer getUnreadMessageCount(Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getUserId, userId);
        wrapper.eq(Message::getStatus, "UNREAD");
        return Math.toIntExact(messageDao.selectCount(wrapper));
    }
}
