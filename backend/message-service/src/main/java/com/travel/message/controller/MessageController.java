package com.travel.message.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.message.entity.Message;
import com.travel.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message")
@Tag(name = "消息服务", description = "提供消息通知相关接口")
public class MessageController {

    @Resource
    private MessageService messageService;

    @Operation(summary = "发送消息", description = "发送消息给用户")
    @PostMapping("/send")
    public R<?> sendMessage(@RequestParam Long userId, @RequestParam String title, 
                          @RequestParam String content, @RequestParam String messageType) {
        messageService.sendMessage(userId, title, content, messageType);
        return R.success();
    }

    @Operation(summary = "获取用户消息列表", description = "分页获取用户消息列表")
    @GetMapping("/user/{userId}")
    public R<?> getUserMessages(@PathVariable Long userId, PageQuery query) {
        return R.success(messageService.getUserMessages(userId, query));
    }

    @Operation(summary = "获取消息详情", description = "根据ID获取消息详情")
    @GetMapping("/{id}")
    public R<?> getMessageById(@PathVariable Long id) {
        return R.success(messageService.getMessageById(id));
    }

    @Operation(summary = "标记消息为已读", description = "根据ID标记消息为已读")
    @PutMapping("/{id}/read")
    public R<?> markMessageAsRead(@PathVariable Long id) {
        messageService.markMessageAsRead(id);
        return R.success();
    }

    @Operation(summary = "删除消息", description = "根据ID删除消息")
    @DeleteMapping("/{id}")
    public R<?> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return R.success();
    }

    @Operation(summary = "获取未读消息数", description = "获取用户未读消息数量")
    @GetMapping("/user/{userId}/unread/count")
    public R<?> getUnreadMessageCount(@PathVariable Long userId) {
        return R.success(messageService.getUnreadMessageCount(userId));
    }
}
