package com.travel.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.trip.dao.TripDao;
import com.travel.trip.dao.TripDetailDao;
import com.travel.trip.dao.TripCollaboratorDao;
import com.travel.trip.dao.TripCommentDao;
import com.travel.trip.dao.TripAttachmentDao;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;
import com.travel.trip.entity.TripCollaborator;
import com.travel.trip.entity.TripComment;
import com.travel.trip.entity.TripAttachment;
import com.travel.trip.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TripServiceImpl implements TripService {

    @Resource
    private TripDao tripDao;

    @Resource
    private TripDetailDao tripDetailDao;

    @Resource
    private TripCollaboratorDao tripCollaboratorDao;

    @Resource
    private TripCommentDao tripCommentDao;

    @Resource
    private TripAttachmentDao tripAttachmentDao;

    @Override
    public PageResult<Trip> getUserTrips(Long userId, PageQuery query) {
        Page<Trip> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Trip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Trip::getUserId, userId);
        wrapper.eq(Trip::getDeleted, 0);
        Page<Trip> result = tripDao.selectPage(page, wrapper);
        return new PageResult<Trip>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public Trip getTripById(Long id) {
        Trip trip = tripDao.selectById(id);
        if (trip != null) {
            // 增加浏览次数
            trip.setViewCount(trip.getViewCount() + 1);
            tripDao.updateById(trip);
        }
        return trip;
    }

    @Override
    @Transactional
    public Trip createTrip(Trip trip) {
        // 设置默认值
        trip.setIsPublic(0);
        trip.setIsShared(0);
        trip.setCollaboratorCount(0);
        trip.setCommentCount(0);
        trip.setViewCount(0);
        trip.setEnabled(1);
        trip.setDeleted(0);
        trip.setCreatedAt(LocalDateTime.now());
        trip.setUpdatedAt(LocalDateTime.now());
        trip.setCreatedBy(trip.getUserId());
        trip.setUpdatedBy(trip.getUserId());
        
        tripDao.insert(trip);
        return trip;
    }

    @Override
    @Transactional
    public Trip updateTrip(Trip trip) {
        trip.setUpdatedAt(LocalDateTime.now());
        tripDao.updateById(trip);
        return trip;
    }

    @Override
    @Transactional
    public void deleteTrip(Long id) {
        // 逻辑删除行程
        Trip trip = tripDao.selectById(id);
        if (trip != null) {
            trip.setDeleted(1);
            trip.setUpdatedAt(LocalDateTime.now());
            tripDao.updateById(trip);
        }
        
        // 删除关联数据
        LambdaQueryWrapper<TripDetail> detailWrapper = new LambdaQueryWrapper<>();
        detailWrapper.eq(TripDetail::getTripId, id);
        tripDetailDao.delete(detailWrapper);
        
        LambdaQueryWrapper<TripCollaborator> collabWrapper = new LambdaQueryWrapper<>();
        collabWrapper.eq(TripCollaborator::getTripId, id);
        tripCollaboratorDao.delete(collabWrapper);
        
        LambdaQueryWrapper<TripComment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(TripComment::getTripId, id);
        tripCommentDao.delete(commentWrapper);
        
        LambdaQueryWrapper<TripAttachment> attachmentWrapper = new LambdaQueryWrapper<>();
        attachmentWrapper.eq(TripAttachment::getTripId, id);
        tripAttachmentDao.delete(attachmentWrapper);
    }

    @Override
    public List<TripDetail> getTripDetails(Long tripId) {
        LambdaQueryWrapper<TripDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripDetail::getTripId, tripId);
        wrapper.eq(TripDetail::getDeleted, 0);
        wrapper.orderByAsc(TripDetail::getDay);
        return tripDetailDao.selectList(wrapper);
    }

    @Override
    public TripDetail getTripDetailById(Long id) {
        return tripDetailDao.selectById(id);
    }

    @Override
    @Transactional
    public TripDetail createTripDetail(TripDetail tripDetail) {
        tripDetail.setStatus(1);
        tripDetail.setDeleted(0);
        tripDetail.setCreatedAt(LocalDateTime.now());
        tripDetail.setUpdatedAt(LocalDateTime.now());
        tripDetailDao.insert(tripDetail);
        return tripDetail;
    }

    @Override
    @Transactional
    public TripDetail updateTripDetail(TripDetail tripDetail) {
        tripDetail.setUpdatedAt(LocalDateTime.now());
        tripDetailDao.updateById(tripDetail);
        return tripDetail;
    }

    @Override
    @Transactional
    public void deleteTripDetail(Long id) {
        TripDetail detail = tripDetailDao.selectById(id);
        if (detail != null) {
            detail.setDeleted(1);
            detail.setUpdatedAt(LocalDateTime.now());
            tripDetailDao.updateById(detail);
        }
    }

    @Override
    public List<TripCollaborator> getTripCollaborators(Long tripId) {
        return tripCollaboratorDao.selectByTripId(tripId);
    }

    @Override
    @Transactional
    public TripCollaborator addCollaborator(TripCollaborator collaborator) {
        collaborator.setJoinedAt(LocalDateTime.now());
        collaborator.setUpdatedAt(LocalDateTime.now());
        tripCollaboratorDao.insert(collaborator);
        
        // 更新行程协作人数
        Trip trip = tripDao.selectById(collaborator.getTripId());
        if (trip != null) {
            trip.setCollaboratorCount(trip.getCollaboratorCount() + 1);
            tripDao.updateById(trip);
        }
        
        return collaborator;
    }

    @Override
    @Transactional
    public TripCollaborator updateCollaborator(Long id, TripCollaborator collaborator) {
        collaborator.setId(id);
        collaborator.setUpdatedAt(LocalDateTime.now());
        tripCollaboratorDao.updateById(collaborator);
        return collaborator;
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
                tripDao.updateById(trip);
            }
        }
    }

    @Override
    public List<Trip> getCollaboratorTrips(Long userId) {
        List<TripCollaborator> collaborators = tripCollaboratorDao.selectByUserId(userId);
        List<Trip> trips = new ArrayList<>();
        
        for (TripCollaborator collaborator : collaborators) {
            Trip trip = tripDao.selectById(collaborator.getTripId());
            if (trip != null && trip.getDeleted() == 0) {
                trips.add(trip);
            }
        }
        
        return trips;
    }

    @Override
    public List<TripComment> getTripComments(Long tripId) {
        return tripCommentDao.selectByTripId(tripId, 50);
    }

    @Override
    @Transactional
    public TripComment addComment(TripComment comment) {
        comment.setStatus(1);
        comment.setDeleted(0);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setCreatedBy(comment.getUserId());
        comment.setUpdatedBy(comment.getUserId());
        tripCommentDao.insert(comment);
        
        // 更新行程评论数
        Trip trip = tripDao.selectById(comment.getTripId());
        if (trip != null) {
            trip.setCommentCount(trip.getCommentCount() + 1);
            tripDao.updateById(trip);
        }
        
        return comment;
    }

    @Override
    @Transactional
    public TripComment updateComment(Long id, TripComment comment) {
        comment.setId(id);
        comment.setUpdatedAt(LocalDateTime.now());
        tripCommentDao.updateById(comment);
        return comment;
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
                tripDao.updateById(trip);
            }
        }
    }

    @Override
    public List<TripAttachment> getTripAttachments(Long tripId) {
        return tripAttachmentDao.selectByTripId(tripId);
    }

    @Override
    public List<TripAttachment> getTripDetailAttachments(Long tripDetailId) {
        return tripAttachmentDao.selectByTripDetailId(tripDetailId);
    }

    @Override
    @Transactional
    public TripAttachment addAttachment(TripAttachment attachment) {
        attachment.setIsPublic(1);
        attachment.setStatus(1);
        attachment.setDeleted(0);
        attachment.setCreatedAt(LocalDateTime.now());
        attachment.setUpdatedAt(LocalDateTime.now());
        attachment.setCreatedBy(attachment.getUserId());
        attachment.setUpdatedBy(attachment.getUserId());
        tripAttachmentDao.insert(attachment);
        return attachment;
    }

    @Override
    @Transactional
    public void deleteAttachment(Long id) {
        TripAttachment attachment = tripAttachmentDao.selectById(id);
        if (attachment != null) {
            attachment.setDeleted(1);
            attachment.setUpdatedAt(LocalDateTime.now());
            tripAttachmentDao.updateById(attachment);
        }
    }

    @Override
    public Map<String, Object> getTripStatistics(Long tripId) {
        Map<String, Object> statistics = new HashMap<>();
        
        Trip trip = tripDao.selectById(tripId);
        if (trip != null) {
            statistics.put("tripId", tripId);
            statistics.put("tripName", trip.getTripName());
            statistics.put("collaboratorCount", trip.getCollaboratorCount());
            statistics.put("commentCount", trip.getCommentCount());
            statistics.put("viewCount", trip.getViewCount());
            statistics.put("attachmentCount", tripAttachmentDao.countByTripId(tripId));
            statistics.put("detailCount", getTripDetails(tripId).size());
        }
        
        return statistics;
    }

    @Override
    public Trip shareTrip(Long tripId, boolean isPublic) {
        Trip trip = tripDao.selectById(tripId);
        if (trip != null) {
            trip.setIsPublic(isPublic ? 1 : 0);
            trip.setIsShared(1);
            trip.setUpdatedAt(LocalDateTime.now());
            tripDao.updateById(trip);
        }
        return trip;
    }

    @Override
    public List<Trip> getPublicTrips(PageQuery query) {
        Page<Trip> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Trip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Trip::getIsPublic, 1);
        wrapper.eq(Trip::getIsShared, 1);
        wrapper.eq(Trip::getDeleted, 0);
        wrapper.orderByDesc(Trip::getViewCount);
        Page<Trip> result = tripDao.selectPage(page, wrapper);
        return result.getRecords();
    }

    @Override
    public void incrementViewCount(Long tripId) {
        Trip trip = tripDao.selectById(tripId);
        if (trip != null) {
            trip.setViewCount(trip.getViewCount() + 1);
            tripDao.updateById(trip);
        }
    }
}

