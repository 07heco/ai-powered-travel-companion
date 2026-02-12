package com.travel.trip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.trip.entity.TripCollaborator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripCollaboratorDao extends BaseMapper<TripCollaborator> {
    List<TripCollaborator> selectByTripId(@Param("tripId") Long tripId);
    List<TripCollaborator> selectByUserId(@Param("userId") Long userId);
    TripCollaborator selectByTripIdAndUserId(@Param("tripId") Long tripId, @Param("userId") Long userId);
    int countByTripId(@Param("tripId") Long tripId);
}