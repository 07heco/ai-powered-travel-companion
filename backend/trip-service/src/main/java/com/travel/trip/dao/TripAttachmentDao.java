package com.travel.trip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.trip.entity.TripAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripAttachmentDao extends BaseMapper<TripAttachment> {
    List<TripAttachment> selectByTripId(@Param("tripId") Long tripId);
    List<TripAttachment> selectByTripDetailId(@Param("tripDetailId") Long tripDetailId);
    List<TripAttachment> selectByUserId(@Param("userId") Long userId);
    List<TripAttachment> selectByFileType(@Param("tripId") Long tripId, @Param("fileType") String fileType);
    int countByTripId(@Param("tripId") Long tripId);
}