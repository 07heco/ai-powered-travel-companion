package com.travel.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.travel.common.vo.R;
import com.travel.user.service.SocialService;
import com.travel.user.entity.UserRelation;
import com.travel.user.entity.TravelCompanionMatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 社交控制器
 */
@Tag(name = "社交接口", description = "处理用户关注、好友关系和旅伴匹配等社交功能")
@Slf4j
@RestController
@RequestMapping("/api/social")
public class SocialController {

    @Resource
    private SocialService socialService;

    /**
     * 关注用户
     */
    @Operation(summary = "关注用户", description = "关注指定的用户")
    @PostMapping("/follow")
    public R<Boolean> followUser(@Parameter(description = "目标用户ID", required = true) @RequestParam Long targetUserId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.followUser(userId, targetUserId);
            return R.success(result);
        } catch (Exception e) {
            log.error("关注用户失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 取消关注
     */
    @Operation(summary = "取消关注", description = "取消对指定用户的关注")
    @PostMapping("/unfollow")
    public R<Boolean> unfollowUser(@Parameter(description = "目标用户ID", required = true) @RequestParam Long targetUserId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.unfollowUser(userId, targetUserId);
            return R.success(result);
        } catch (Exception e) {
            log.error("取消关注失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 发送好友请求
     */
    @Operation(summary = "发送好友请求", description = "向指定用户发送好友请求")
    @PostMapping("/friend/request")
    public R<Boolean> sendFriendRequest(@Parameter(description = "目标用户ID", required = true) @RequestParam Long targetUserId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.sendFriendRequest(userId, targetUserId);
            return R.success(result);
        } catch (Exception e) {
            log.error("发送好友请求失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 接受好友请求
     */
    @Operation(summary = "接受好友请求", description = "接受指定的好友请求")
    @PostMapping("/friend/accept")
    public R<Boolean> acceptFriendRequest(@Parameter(description = "请求ID", required = true) @RequestParam Long requestId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.acceptFriendRequest(userId, requestId);
            return R.success(result);
        } catch (Exception e) {
            log.error("接受好友请求失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 拒绝好友请求
     */
    @Operation(summary = "拒绝好友请求", description = "拒绝指定的好友请求")
    @PostMapping("/friend/reject")
    public R<Boolean> rejectFriendRequest(@Parameter(description = "请求ID", required = true) @RequestParam Long requestId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.rejectFriendRequest(userId, requestId);
            return R.success(result);
        } catch (Exception e) {
            log.error("拒绝好友请求失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 删除好友
     */
    @Operation(summary = "删除好友", description = "删除指定的好友")
    @DeleteMapping("/friend")
    public R<Boolean> deleteFriend(@Parameter(description = "目标用户ID", required = true) @RequestParam Long targetUserId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.deleteFriend(userId, targetUserId);
            return R.success(result);
        } catch (Exception e) {
            log.error("删除好友失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 加入黑名单
     */
    @Operation(summary = "加入黑名单", description = "将指定用户加入黑名单")
    @PostMapping("/blacklist/add")
    public R<Boolean> addToBlacklist(@Parameter(description = "目标用户ID", required = true) @RequestParam Long targetUserId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.addToBlacklist(userId, targetUserId);
            return R.success(result);
        } catch (Exception e) {
            log.error("加入黑名单失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 移除黑名单
     */
    @Operation(summary = "移除黑名单", description = "将指定用户从黑名单中移除")
    @PostMapping("/blacklist/remove")
    public R<Boolean> removeFromBlacklist(@Parameter(description = "目标用户ID", required = true) @RequestParam Long targetUserId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.removeFromBlacklist(userId, targetUserId);
            return R.success(result);
        } catch (Exception e) {
            log.error("移除黑名单失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取关注列表
     */
    @Operation(summary = "获取关注列表", description = "获取当前用户关注的用户列表")
    @GetMapping("/following")
    public R<List<UserRelation>> getFollowingList(
            @Parameter(description = "页码，默认1") @RequestParam(defaultValue = "1") Integer page, 
            @Parameter(description = "每页数量，默认20") @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            List<UserRelation> list = socialService.getFollowingList(userId, page, size);
            return R.success(list);
        } catch (Exception e) {
            log.error("获取关注列表失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取粉丝列表
     */
    @Operation(summary = "获取粉丝列表", description = "获取当前用户的粉丝列表")
    @GetMapping("/followers")
    public R<List<UserRelation>> getFollowerList(
            @Parameter(description = "页码，默认1") @RequestParam(defaultValue = "1") Integer page, 
            @Parameter(description = "每页数量，默认20") @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            List<UserRelation> list = socialService.getFollowerList(userId, page, size);
            return R.success(list);
        } catch (Exception e) {
            log.error("获取粉丝列表失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取好友列表
     */
    @Operation(summary = "获取好友列表", description = "获取当前用户的好友列表")
    @GetMapping("/friends")
    public R<List<UserRelation>> getFriendList(
            @Parameter(description = "页码，默认1") @RequestParam(defaultValue = "1") Integer page, 
            @Parameter(description = "每页数量，默认20") @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            List<UserRelation> list = socialService.getFriendList(userId, page, size);
            return R.success(list);
        } catch (Exception e) {
            log.error("获取好友列表失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取黑名单
     */
    @Operation(summary = "获取黑名单", description = "获取当前用户的黑名单列表")
    @GetMapping("/blacklist")
    public R<List<UserRelation>> getBlacklist(
            @Parameter(description = "页码，默认1") @RequestParam(defaultValue = "1") Integer page, 
            @Parameter(description = "每页数量，默认20") @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            List<UserRelation> list = socialService.getBlacklist(userId, page, size);
            return R.success(list);
        } catch (Exception e) {
            log.error("获取黑名单失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 推荐旅伴
     */
    @Operation(summary = "推荐旅伴", description = "获取推荐的旅伴列表")
    @GetMapping("/match/recommend")
    public R<List<TravelCompanionMatch>> recommendTravelCompanions(
            @Parameter(description = "页码，默认1") @RequestParam(defaultValue = "1") Integer page, 
            @Parameter(description = "每页数量，默认20") @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            List<TravelCompanionMatch> list = socialService.recommendTravelCompanions(userId, page, size);
            return R.success(list);
        } catch (Exception e) {
            log.error("推荐旅伴失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 确认旅伴匹配
     */
    @Operation(summary = "确认旅伴匹配", description = "确认指定的旅伴匹配")
    @PostMapping("/match/confirm")
    public R<Boolean> confirmMatch(@Parameter(description = "匹配ID", required = true) @RequestParam Long matchId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.confirmMatch(userId, matchId);
            return R.success(result);
        } catch (Exception e) {
            log.error("确认旅伴匹配失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 拒绝旅伴匹配
     */
    @Operation(summary = "拒绝旅伴匹配", description = "拒绝指定的旅伴匹配")
    @PostMapping("/match/reject")
    public R<Boolean> rejectMatch(@Parameter(description = "匹配ID", required = true) @RequestParam Long matchId) {
        try {
            Long userId = StpUtil.getLoginIdAsLong();
            boolean result = socialService.rejectMatch(userId, matchId);
            return R.success(result);
        } catch (Exception e) {
            log.error("拒绝旅伴匹配失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }
}
