package com.travel.trip.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;
import com.travel.trip.entity.TripCollaborator;
import com.travel.trip.entity.TripComment;
import com.travel.trip.entity.TripAttachment;

import java.util.List;
import java.util.Map;

public interface TripService {
    // 行程基本操作
    PageResult<Trip> getUserTrips(Long userId, PageQuery query);
    Trip getTripById(Long id);
    Trip createTrip(Trip trip);
    Trip updateTrip(Trip trip);
    void deleteTrip(Long id);
    
    // 行程详情操作
    List<TripDetail> getTripDetails(Long tripId);
    TripDetail getTripDetailById(Long id);
    TripDetail createTripDetail(TripDetail tripDetail);
    TripDetail updateTripDetail(TripDetail tripDetail);
    void deleteTripDetail(Long id);
    
    // 行程协作操作
    List<TripCollaborator> getTripCollaborators(Long tripId);
    TripCollaborator addCollaborator(TripCollaborator collaborator);
    TripCollaborator updateCollaborator(Long id, TripCollaborator collaborator);
    void removeCollaborator(Long id);
    List<Trip> getCollaboratorTrips(Long userId);
    
    // 行程评论操作
    List<TripComment> getTripComments(Long tripId);
    TripComment addComment(TripComment comment);
    TripComment updateComment(Long id, TripComment comment);
    void deleteComment(Long id);
    
    // 行程附件操作
    List<TripAttachment> getTripAttachments(Long tripId);
    List<TripAttachment> getTripDetailAttachments(Long tripDetailId);
    TripAttachment addAttachment(TripAttachment attachment);
    void deleteAttachment(Long id);
    
    // 行程统计和分享
    Map<String, Object> getTripStatistics(Long tripId);
    Trip shareTrip(Long tripId, boolean isPublic);
    List<Trip> getPublicTrips(PageQuery query);
    void incrementViewCount(Long tripId);
}
