package com.travel.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.trip.dao.*;
import com.travel.trip.entity.*;
import com.travel.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;
    private final TripDetailDao tripDetailDao;
    private final TripCommentDao tripCommentDao;
    private final TripCollaboratorDao tripCollaboratorDao;
    private final TripAttachmentDao tripAttachmentDao;

    // 行程基本操作
    @Override
    public PageResult<Trip> getUserTrips(Long userId, PageQuery query) {
        LambdaQueryWrapper<Trip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Trip::getUserId, userId)
                .eq(Trip::getDeleted, 0)
                .orderByDesc(Trip::getCreatedAt);

        IPage<Trip> page = new Page<>(query.getPage(), query.getSize());
        IPage<Trip> result = tripDao.selectPage(page, wrapper);

        return new PageResult<Trip>(result.getTotal(), Integer.valueOf((int) result.getSize()), Integer.valueOf((int) result.getCurrent()), result.getRecords());
    }

    @Override
    public Trip getTripById(Long id) {
        Trip trip = tripDao.selectById(id);
        if (trip != null && trip.getDeleted() == 0) {
            // 增加浏览次数
            incrementViewCount(id);
            return trip;
        }
        return null;
    }

    @Override
    @Transactional
    public Trip createTrip(Trip trip) {
        trip.setCreatedAt(LocalDateTime.now());
        trip.setUpdatedAt(LocalDateTime.now());
        trip.setCreatedBy(trip.getUserId());
        trip.setUpdatedBy(trip.getUserId());
        trip.setCollaboratorCount(0);
        trip.setCommentCount(0);
        trip.setViewCount(0);
        trip.setDeleted(0);
        trip.setStatus(1);
        trip.setIsPublic(0);
        trip.setIsShared(0);
        trip.setBudget(trip.getBudget() != null ? trip.getBudget() : java.math.BigDecimal.ZERO);

        tripDao.insert(trip);
        return trip;
    }

    @Override
    @Transactional
    public Trip updateTrip(Trip trip) {
        trip.setUpdatedAt(LocalDateTime.now());
        tripDao.updateById(trip);
        return tripDao.selectById(trip.getId());
    }

    @Override
    @Transactional
    public void deleteTrip(Long id) {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDeleted(1);
        trip.setUpdatedAt(LocalDateTime.now());
        tripDao.updateById(trip);
    }

    // 行程详情操作
    @Override
    public List<TripDetail> getTripDetails(Long tripId) {
        LambdaQueryWrapper<TripDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripDetail::getTripId, tripId)
                .eq(TripDetail::getDeleted, 0)
                .orderByAsc(TripDetail::getDay);
        return tripDetailDao.selectList(wrapper);
    }

    @Override
    public TripDetail getTripDetailById(Long id) {
        TripDetail tripDetail = tripDetailDao.selectById(id);
        return tripDetail != null && tripDetail.getDeleted() == 0 ? tripDetail : null;
    }

    @Override
    @Transactional
    public TripDetail createTripDetail(TripDetail tripDetail) {
        tripDetail.setCreatedAt(LocalDateTime.now());
        tripDetail.setUpdatedAt(LocalDateTime.now());
        tripDetail.setDeleted(0);
        tripDetail.setStatus(1);
        tripDetail.setCost(tripDetail.getCost() != null ? tripDetail.getCost() : java.math.BigDecimal.ZERO);

        tripDetailDao.insert(tripDetail);
        return tripDetail;
    }

    @Override
    @Transactional
    public TripDetail updateTripDetail(TripDetail tripDetail) {
        tripDetail.setUpdatedAt(LocalDateTime.now());
        tripDetailDao.updateById(tripDetail);
        return tripDetailDao.selectById(tripDetail.getId());
    }

    @Override
    @Transactional
    public void deleteTripDetail(Long id) {
        TripDetail tripDetail = new TripDetail();
        tripDetail.setId(id);
        tripDetail.setDeleted(1);
        tripDetail.setUpdatedAt(LocalDateTime.now());
        tripDetailDao.updateById(tripDetail);
    }

    // 行程评论操作
    @Override
    public List<TripComment> getTripComments(Long tripId) {
        LambdaQueryWrapper<TripComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripComment::getTripId, tripId)
                .eq(TripComment::getDeleted, 0)
                .eq(TripComment::getStatus, 1)
                .orderByAsc(TripComment::getCreatedAt);
        return tripCommentDao.selectList(wrapper);
    }

    @Override
    @Transactional
    public TripComment addComment(TripComment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setDeleted(0);
        comment.setStatus(1);
        comment.setCreatedBy(comment.getUserId());
        comment.setUpdatedBy(comment.getUserId());

        tripCommentDao.insert(comment);

        // 更新行程评论数
        Trip trip = tripDao.selectById(comment.getTripId());
        if (trip != null) {
            trip.setCommentCount(trip.getCommentCount() + 1);
            trip.setUpdatedAt(LocalDateTime.now());
            tripDao.updateById(trip);
        }

        return comment;
    }

    @Override
    @Transactional
    public TripComment updateComment(Long id, TripComment comment) {
        TripComment existingComment = tripCommentDao.selectById(id);
        if (existingComment != null && existingComment.getDeleted() == 0) {
            existingComment.setContent(comment.getContent());
            existingComment.setUpdatedAt(LocalDateTime.now());
            existingComment.setUpdatedBy(comment.getUserId());
            tripCommentDao.updateById(existingComment);
            return existingComment;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        TripComment comment = tripCommentDao.selectById(id);
        if (comment != null) {
            comment.setDeleted(1);
            comment.setUpdatedAt(LocalDateTime.now());
            tripCommentDao.updateById(comment);

            // 更新行程评论数
            Trip trip = tripDao.selectById(comment.getTripId());
            if (trip != null && trip.getCommentCount() > 0) {
                trip.setCommentCount(trip.getCommentCount() - 1);
                trip.setUpdatedAt(LocalDateTime.now());
                tripDao.updateById(trip);
            }
        }
    }

    // 行程协作操作
    @Override
    public List<TripCollaborator> getTripCollaborators(Long tripId) {
        LambdaQueryWrapper<TripCollaborator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripCollaborator::getTripId, tripId)
                .orderByAsc(TripCollaborator::getJoinedAt);
        return tripCollaboratorDao.selectList(wrapper);
    }

    @Override
    @Transactional
    public TripCollaborator addCollaborator(TripCollaborator collaborator) {
        collaborator.setJoinedAt(LocalDateTime.now());
        collaborator.setUpdatedAt(LocalDateTime.now());
        collaborator.setRole(collaborator.getRole() != null ? collaborator.getRole() : "member");
        collaborator.setPermissions(collaborator.getPermissions() != null ? collaborator.getPermissions() : "view");

        tripCollaboratorDao.insert(collaborator);

        // 更新行程协作人数
        Trip trip = tripDao.selectById(collaborator.getTripId());
        if (trip != null) {
            trip.setCollaboratorCount(trip.getCollaboratorCount() + 1);
            trip.setUpdatedAt(LocalDateTime.now());
            tripDao.updateById(trip);
        }

        return collaborator;
    }

    @Override
    @Transactional
    public TripCollaborator updateCollaborator(Long id, TripCollaborator collaborator) {
        TripCollaborator existingCollaborator = tripCollaboratorDao.selectById(id);
        if (existingCollaborator != null) {
            existingCollaborator.setRole(collaborator.getRole());
            existingCollaborator.setPermissions(collaborator.getPermissions());
            existingCollaborator.setUpdatedAt(LocalDateTime.now());
            tripCollaboratorDao.updateById(existingCollaborator);
            return existingCollaborator;
        }
        return null;
    }

    @Override
    @Transactional
    public void removeCollaborator(Long id) {
        TripCollaborator collaborator = tripCollaboratorDao.selectById(id);
        if (collaborator != null) {
            tripCollaboratorDao.deleteById(id);

            // 更新行程协作人数
            Trip trip = tripDao.selectById(collaborator.getTripId());
            if (trip != null && trip.getCollaboratorCount() > 0) {
                trip.setCollaboratorCount(trip.getCollaboratorCount() - 1);
                trip.setUpdatedAt(LocalDateTime.now());
                tripDao.updateById(trip);
            }
        }
    }

    // 行程附件操作
    @Override
    public List<TripAttachment> getTripAttachments(Long tripId) {
        LambdaQueryWrapper<TripAttachment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripAttachment::getTripId, tripId)
                .eq(TripAttachment::getDeleted, 0)
                .eq(TripAttachment::getStatus, 1)
                .orderByDesc(TripAttachment::getCreatedAt);
        return tripAttachmentDao.selectList(wrapper);
    }

    @Override
    @Transactional
    public TripAttachment addAttachment(TripAttachment attachment) {
        attachment.setCreatedAt(LocalDateTime.now());
        attachment.setUpdatedAt(LocalDateTime.now());
        attachment.setDeleted(0);
        attachment.setStatus(1);
        attachment.setCreatedBy(attachment.getUserId());
        attachment.setUpdatedBy(attachment.getUserId());
        attachment.setIsPublic(1);

        tripAttachmentDao.insert(attachment);
        return attachment;
    }

    @Override
    @Transactional
    public void deleteAttachment(Long id) {
        TripAttachment attachment = new TripAttachment();
        attachment.setId(id);
        attachment.setDeleted(1);
        attachment.setUpdatedAt(LocalDateTime.now());
        tripAttachmentDao.updateById(attachment);
    }

    // 行程统计和分享
    @Override
    public Map<String, Object> getTripStatistics(Long tripId) {
        Map<String, Object> statistics = new HashMap<>();

        // 获取行程基本信息
        Trip trip = tripDao.selectById(tripId);
        if (trip == null || trip.getDeleted() == 1) {
            return statistics;
        }

        // 统计行程详情数
        int detailCount = getTripDetails(tripId).size();

        // 统计评论数
        int commentCount = getTripComments(tripId).size();

        // 统计协作人数
        int collaboratorCount = getTripCollaborators(tripId).size();

        // 统计附件数
        int attachmentCount = getTripAttachments(tripId).size();

        // 计算总费用
        double totalCost = 0;
        List<TripDetail> details = getTripDetails(tripId);
        for (TripDetail detail : details) {
            if (detail.getCost() != null) {
                totalCost += detail.getCost().doubleValue();
            }
        }

        statistics.put("tripId", tripId);
        statistics.put("tripName", trip.getTripName());
        statistics.put("viewCount", trip.getViewCount());
        statistics.put("commentCount", commentCount);
        statistics.put("collaboratorCount", collaboratorCount);
        statistics.put("attachmentCount", attachmentCount);
        statistics.put("detailCount", detailCount);
        statistics.put("totalCost", totalCost);
        statistics.put("budget", trip.getBudget());
        statistics.put("startDate", trip.getStartDate());
        statistics.put("endDate", trip.getEndDate());

        return statistics;
    }

    @Override
    @Transactional
    public Trip shareTrip(Long tripId, boolean isPublic) {
        Trip trip = tripDao.selectById(tripId);
        if (trip != null && trip.getDeleted() == 0) {
            trip.setIsPublic(isPublic ? 1 : 0);
            trip.setIsShared(1);
            trip.setUpdatedAt(LocalDateTime.now());
            tripDao.updateById(trip);
            return trip;
        }
        return null;
    }

    @Override
    public List<Trip> getPublicTrips(PageQuery query) {
        LambdaQueryWrapper<Trip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Trip::getIsPublic, 1)
                .eq(Trip::getIsShared, 1)
                .eq(Trip::getDeleted, 0)
                .eq(Trip::getStatus, 1)
                .orderByDesc(Trip::getCreatedAt);

        IPage<Trip> page = new Page<>(query.getPage(), query.getSize());
        IPage<Trip> result = tripDao.selectPage(page, wrapper);

        return result.getRecords();
    }

    @Override
    @Transactional
    public void incrementViewCount(Long tripId) {
        Trip trip = tripDao.selectById(tripId);
        if (trip != null && trip.getDeleted() == 0) {
            trip.setViewCount(trip.getViewCount() + 1);
            trip.setUpdatedAt(LocalDateTime.now());
            tripDao.updateById(trip);
        }
    }
}
