package com.travel.message.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.message.entity.Message;

public interface MessageService {
    void sendMessage(Long userId, String title, String content, String messageType);
    PageResult<Message> getUserMessages(Long userId, PageQuery query);
    Message getMessageById(Long id);
    void markMessageAsRead(Long messageId);
    void deleteMessage(Long messageId);
    Integer getUnreadMessageCount(Long userId);
}
