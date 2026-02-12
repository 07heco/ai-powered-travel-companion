package com.travel.user.controller;

import com.travel.common.vo.R;
import com.travel.user.service.SocialService;
import com.travel.user.entity.UserRelation;
import com.travel.user.entity.TravelCompanionMatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 社交控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/social")
public class SocialController {

    @Resource
    private SocialService socialService;

    /**
     * 关注用户
     */
    @PostMapping("/follow")
    public R<Boolean> followUser(HttpServletRequest request, @RequestParam Long targetUserId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/unfollow")
    public R<Boolean> unfollowUser(HttpServletRequest request, @RequestParam Long targetUserId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/friend/request")
    public R<Boolean> sendFriendRequest(HttpServletRequest request, @RequestParam Long targetUserId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/friend/accept")
    public R<Boolean> acceptFriendRequest(HttpServletRequest request, @RequestParam Long requestId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/friend/reject")
    public R<Boolean> rejectFriendRequest(HttpServletRequest request, @RequestParam Long requestId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @DeleteMapping("/friend")
    public R<Boolean> deleteFriend(HttpServletRequest request, @RequestParam Long targetUserId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/blacklist/add")
    public R<Boolean> addToBlacklist(HttpServletRequest request, @RequestParam Long targetUserId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/blacklist/remove")
    public R<Boolean> removeFromBlacklist(HttpServletRequest request, @RequestParam Long targetUserId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @GetMapping("/following")
    public R<List<UserRelation>> getFollowingList(HttpServletRequest request, 
                                                 @RequestParam(defaultValue = "1") Integer page, 
                                                 @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @GetMapping("/followers")
    public R<List<UserRelation>> getFollowerList(HttpServletRequest request, 
                                                 @RequestParam(defaultValue = "1") Integer page, 
                                                 @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @GetMapping("/friends")
    public R<List<UserRelation>> getFriendList(HttpServletRequest request, 
                                              @RequestParam(defaultValue = "1") Integer page, 
                                              @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @GetMapping("/blacklist")
    public R<List<UserRelation>> getBlacklist(HttpServletRequest request, 
                                             @RequestParam(defaultValue = "1") Integer page, 
                                             @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @GetMapping("/match/recommend")
    public R<List<TravelCompanionMatch>> recommendTravelCompanions(HttpServletRequest request, 
                                                                  @RequestParam(defaultValue = "1") Integer page, 
                                                                  @RequestParam(defaultValue = "20") Integer size) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/match/confirm")
    public R<Boolean> confirmMatch(HttpServletRequest request, @RequestParam Long matchId) {
        try {
            Long userId = getUserIdFromRequest(request);
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
    @PostMapping("/match/reject")
    public R<Boolean> rejectMatch(HttpServletRequest request, @RequestParam Long matchId) {
        try {
            Long userId = getUserIdFromRequest(request);
            boolean result = socialService.rejectMatch(userId, matchId);
            return R.success(result);
        } catch (Exception e) {
            log.error("拒绝旅伴匹配失败: {}", e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 从请求中获取用户ID
     */
    private Long getUserIdFromRequest(HttpServletRequest request) {
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            throw new RuntimeException("用户未登录");
        }
        return Long.parseLong(userIdStr);
    }
}
