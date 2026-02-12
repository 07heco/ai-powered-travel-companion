package com.travel.user.service;

import com.travel.user.entity.UserRelation;
import com.travel.user.entity.TravelCompanionMatch;

import java.util.List;

/**
 * 社交服务
 */
public interface SocialService {

    /**
     * 关注用户
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @return 是否关注成功
     */
    boolean followUser(Long userId, Long targetUserId);

    /**
     * 取消关注
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @return 是否取消成功
     */
    boolean unfollowUser(Long userId, Long targetUserId);

    /**
     * 发送好友请求
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @return 是否发送成功
     */
    boolean sendFriendRequest(Long userId, Long targetUserId);

    /**
     * 接受好友请求
     * @param userId 当前用户ID
     * @param requestId 请求ID
     * @return 是否接受成功
     */
    boolean acceptFriendRequest(Long userId, Long requestId);

    /**
     * 拒绝好友请求
     * @param userId 当前用户ID
     * @param requestId 请求ID
     * @return 是否拒绝成功
     */
    boolean rejectFriendRequest(Long userId, Long requestId);

    /**
     * 删除好友
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @return 是否删除成功
     */
    boolean deleteFriend(Long userId, Long targetUserId);

    /**
     * 加入黑名单
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @return 是否加入成功
     */
    boolean addToBlacklist(Long userId, Long targetUserId);

    /**
     * 移除黑名单
     * @param userId 当前用户ID
     * @param targetUserId 目标用户ID
     * @return 是否移除成功
     */
    boolean removeFromBlacklist(Long userId, Long targetUserId);

    /**
     * 获取用户关注列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 关注列表
     */
    List<UserRelation> getFollowingList(Long userId, Integer page, Integer size);

    /**
     * 获取用户粉丝列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 粉丝列表
     */
    List<UserRelation> getFollowerList(Long userId, Integer page, Integer size);

    /**
     * 获取用户好友列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 好友列表
     */
    List<UserRelation> getFriendList(Long userId, Integer page, Integer size);

    /**
     * 获取用户黑名单列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 黑名单列表
     */
    List<UserRelation> getBlacklist(Long userId, Integer page, Integer size);

    /**
     * 推荐旅伴
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 推荐列表
     */
    List<TravelCompanionMatch> recommendTravelCompanions(Long userId, Integer page, Integer size);

    /**
     * 确认旅伴匹配
     * @param userId 用户ID
     * @param matchId 匹配ID
     * @return 是否确认成功
     */
    boolean confirmMatch(Long userId, Long matchId);

    /**
     * 拒绝旅伴匹配
     * @param userId 用户ID
     * @param matchId 匹配ID
     * @return 是否拒绝成功
     */
    boolean rejectMatch(Long userId, Long matchId);
}
