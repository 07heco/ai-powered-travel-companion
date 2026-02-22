package com.travel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.travel.user.dao.UserDao;
import com.travel.user.dao.UserRelationDao;
import com.travel.user.dao.TravelCompanionMatchDao;
import com.travel.user.entity.User;
import com.travel.user.entity.UserRelation;
import com.travel.user.entity.TravelCompanionMatch;
import com.travel.user.service.SocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * 社交服务实现
 */
@Slf4j
@Service
public class SocialServiceImpl implements SocialService {

    @Resource
    private UserRelationDao userRelationDao;

    @Resource
    private TravelCompanionMatchDao travelCompanionMatchDao;

    @Resource
    private UserDao userDao;

    private final Random random = new Random();

    @Override
    @Transactional
    public boolean followUser(Long userId, Long targetUserId) {
        // 检查用户是否存在
        if (!checkUserExists(targetUserId)) {
            throw new RuntimeException("目标用户不存在");
        }

        // 检查是否已经关注
        UserRelation existing = userRelationDao.selectOne(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getToUserId, targetUserId)
                .eq(UserRelation::getType, 1)
                .eq(UserRelation::getDeleted, 0)
        );

        if (existing != null) {
            return true; // 已经关注，直接返回成功
        }

        // 创建关注关系
        UserRelation relation = new UserRelation();
        relation.setFromUserId(userId);
        relation.setToUserId(targetUserId);
        relation.setType(1); // 1-关注
        relation.setStatus(1); // 1-已确认
        relation.setCreateTime(LocalDateTime.now());
        relation.setUpdateTime(LocalDateTime.now());
        relation.setDeleted(0);

        int result = userRelationDao.insert(relation);
        log.info("用户关注成功: userId={}, targetUserId={}", userId, targetUserId);
        return result > 0;
    }

    @Override
    public boolean unfollowUser(Long userId, Long targetUserId) {
        int result = userRelationDao.delete(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getToUserId, targetUserId)
                .eq(UserRelation::getType, 1)
        );
        log.info("用户取消关注成功: userId={}, targetUserId={}", userId, targetUserId);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean sendFriendRequest(Long userId, Long targetUserId) {
        // 检查用户是否存在
        if (!checkUserExists(targetUserId)) {
            throw new RuntimeException("目标用户不存在");
        }

        // 检查是否已经是好友
        UserRelation existing = userRelationDao.selectOne(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getToUserId, targetUserId)
                .eq(UserRelation::getType, 2)
                .eq(UserRelation::getStatus, 1)
                .eq(UserRelation::getDeleted, 0)
        );

        if (existing != null) {
            return true; // 已经是好友，直接返回成功
        }

        // 检查是否已经发送过请求
        UserRelation pending = userRelationDao.selectOne(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getToUserId, targetUserId)
                .eq(UserRelation::getType, 2)
                .eq(UserRelation::getStatus, 0)
                .eq(UserRelation::getDeleted, 0)
        );

        if (pending != null) {
            return true; // 已经发送过请求，直接返回成功
        }

        // 创建好友请求
        UserRelation relation = new UserRelation();
        relation.setFromUserId(userId);
        relation.setToUserId(targetUserId);
        relation.setType(2); // 2-好友
        relation.setStatus(0); // 0-待确认
        relation.setCreateTime(LocalDateTime.now());
        relation.setUpdateTime(LocalDateTime.now());
        relation.setDeleted(0);

        int result = userRelationDao.insert(relation);
        log.info("发送好友请求成功: userId={}, targetUserId={}", userId, targetUserId);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean acceptFriendRequest(Long userId, Long requestId) {
        // 获取好友请求
        UserRelation request = userRelationDao.selectById(requestId);
        if (request == null || request.getToUserId().equals(userId) || request.getStatus() != 0) {
            throw new RuntimeException("好友请求不存在或已处理");
        }

        // 更新请求状态
        request.setStatus(1);
        request.setUpdateTime(LocalDateTime.now());
        userRelationDao.updateById(request);

        // 创建反向好友关系
        UserRelation reverseRelation = new UserRelation();
        reverseRelation.setFromUserId(userId);
        reverseRelation.setToUserId(request.getFromUserId());
        reverseRelation.setType(2);
        reverseRelation.setStatus(1);
        reverseRelation.setCreateTime(LocalDateTime.now());
        reverseRelation.setUpdateTime(LocalDateTime.now());
        reverseRelation.setDeleted(0);
        userRelationDao.insert(reverseRelation);

        log.info("接受好友请求成功: userId={}, requestId={}", userId, requestId);
        return true;
    }

    @Override
    public boolean rejectFriendRequest(Long userId, Long requestId) {
        UserRelation request = userRelationDao.selectById(requestId);
        if (request == null || request.getToUserId().equals(userId) || request.getStatus() != 0) {
            throw new RuntimeException("好友请求不存在或已处理");
        }

        // 删除请求
        userRelationDao.deleteById(requestId);
        log.info("拒绝好友请求成功: userId={}, requestId={}", userId, requestId);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteFriend(Long userId, Long targetUserId) {
        // 删除双向好友关系
        int result1 = userRelationDao.delete(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getToUserId, targetUserId)
                .eq(UserRelation::getType, 2)
        );

        int result2 = userRelationDao.delete(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, targetUserId)
                .eq(UserRelation::getToUserId, userId)
                .eq(UserRelation::getType, 2)
        );

        log.info("删除好友成功: userId={}, targetUserId={}", userId, targetUserId);
        return result1 > 0 || result2 > 0;
    }

    @Override
    @Transactional
    public boolean addToBlacklist(Long userId, Long targetUserId) {
        // 检查用户是否存在
        if (!checkUserExists(targetUserId)) {
            throw new RuntimeException("目标用户不存在");
        }

        // 检查是否已经在黑名单中
        UserRelation existing = userRelationDao.selectOne(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getToUserId, targetUserId)
                .eq(UserRelation::getType, 3)
                .eq(UserRelation::getDeleted, 0)
        );

        if (existing != null) {
            return true; // 已经在黑名单中，直接返回成功
        }

        // 创建黑名单关系
        UserRelation relation = new UserRelation();
        relation.setFromUserId(userId);
        relation.setToUserId(targetUserId);
        relation.setType(3); // 3-黑名单
        relation.setStatus(1);
        relation.setCreateTime(LocalDateTime.now());
        relation.setUpdateTime(LocalDateTime.now());
        relation.setDeleted(0);

        int result = userRelationDao.insert(relation);
        log.info("加入黑名单成功: userId={}, targetUserId={}", userId, targetUserId);
        return result > 0;
    }

    @Override
    public boolean removeFromBlacklist(Long userId, Long targetUserId) {
        int result = userRelationDao.delete(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getToUserId, targetUserId)
                .eq(UserRelation::getType, 3)
        );
        log.info("移除黑名单成功: userId={}, targetUserId={}", userId, targetUserId);
        return result > 0;
    }

    @Override
    public List<UserRelation> getFollowingList(Long userId, Integer page, Integer size) {
        // 实现分页查询关注列表
        // 这里简化实现，实际项目中应该使用Page对象
        return userRelationDao.selectList(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getType, 1)
                .eq(UserRelation::getStatus, 1)
                .eq(UserRelation::getDeleted, 0)
                .orderByDesc(UserRelation::getCreateTime)
        );
    }

    @Override
    public List<UserRelation> getFollowerList(Long userId, Integer page, Integer size) {
        return userRelationDao.selectList(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getToUserId, userId)
                .eq(UserRelation::getType, 1)
                .eq(UserRelation::getStatus, 1)
                .eq(UserRelation::getDeleted, 0)
                .orderByDesc(UserRelation::getCreateTime)
        );
    }

    @Override
    public List<UserRelation> getFriendList(Long userId, Integer page, Integer size) {
        return userRelationDao.selectList(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getType, 2)
                .eq(UserRelation::getStatus, 1)
                .eq(UserRelation::getDeleted, 0)
                .orderByDesc(UserRelation::getCreateTime)
        );
    }

    @Override
    public List<UserRelation> getBlacklist(Long userId, Integer page, Integer size) {
        return userRelationDao.selectList(new LambdaQueryWrapper<UserRelation>()
                .eq(UserRelation::getFromUserId, userId)
                .eq(UserRelation::getType, 3)
                .eq(UserRelation::getDeleted, 0)
                .orderByDesc(UserRelation::getCreateTime)
        );
    }

    @Override
    public List<TravelCompanionMatch> recommendTravelCompanions(Long userId, Integer page, Integer size) {
        // 简化实现：随机生成一些推荐
        // 实际项目中应该基于兴趣爱好、旅行计划等进行匹配
        List<User> allUsers = userDao.selectList(new LambdaQueryWrapper<User>()
                .ne(User::getId, userId)
                .eq(User::getStatus, 1)
                .eq(User::getDeleted, 0)
        );

        for (User user : allUsers) {
            // 检查是否已经匹配过
            TravelCompanionMatch existing = travelCompanionMatchDao.selectOne(new LambdaQueryWrapper<TravelCompanionMatch>()
                    .eq(TravelCompanionMatch::getUserId, userId)
                    .eq(TravelCompanionMatch::getMatchUserId, user.getId())
                    .eq(TravelCompanionMatch::getDeleted, 0)
            );

            if (existing == null) {
                // 创建匹配记录
                TravelCompanionMatch match = new TravelCompanionMatch();
                match.setUserId(userId);
                match.setMatchUserId(user.getId());
                match.setMatchScore(random.nextInt(50) + 50); // 50-100分
                match.setMatchReason("基于共同兴趣推荐");
                match.setStatus(0); // 0-待确认
                match.setCreateTime(LocalDateTime.now());
                match.setUpdateTime(LocalDateTime.now());
                match.setDeleted(0);
                travelCompanionMatchDao.insert(match);
            }
        }

        // 返回推荐列表
        return travelCompanionMatchDao.selectList(new LambdaQueryWrapper<TravelCompanionMatch>()
                .eq(TravelCompanionMatch::getUserId, userId)
                .eq(TravelCompanionMatch::getStatus, 0)
                .eq(TravelCompanionMatch::getDeleted, 0)
                .orderByDesc(TravelCompanionMatch::getMatchScore)
        );
    }

    @Override
    public boolean confirmMatch(Long userId, Long matchId) {
        TravelCompanionMatch match = travelCompanionMatchDao.selectById(matchId);
        if (match == null || !match.getUserId().equals(userId) || match.getStatus() != 0) {
            throw new RuntimeException("匹配记录不存在或已处理");
        }

        match.setStatus(1); // 1-已确认
        match.setUpdateTime(LocalDateTime.now());
        int result = travelCompanionMatchDao.updateById(match);
        log.info("确认旅伴匹配成功: userId={}, matchId={}", userId, matchId);
        return result > 0;
    }

    @Override
    public boolean rejectMatch(Long userId, Long matchId) {
        TravelCompanionMatch match = travelCompanionMatchDao.selectById(matchId);
        if (match == null || !match.getUserId().equals(userId) || match.getStatus() != 0) {
            throw new RuntimeException("匹配记录不存在或已处理");
        }

        match.setStatus(2); // 2-已拒绝
        match.setUpdateTime(LocalDateTime.now());
        int result = travelCompanionMatchDao.updateById(match);
        log.info("拒绝旅伴匹配成功: userId={}, matchId={}", userId, matchId);
        return result > 0;
    }

    /**
     * 检查用户是否存在
     */
    private boolean checkUserExists(Long userId) {
        User user = userDao.selectById(userId);
        return user != null && user.getStatus() == 1 && user.getDeleted() == 0;
    }
}
