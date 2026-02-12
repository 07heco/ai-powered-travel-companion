package com.travel.trip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.trip.entity.TripComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripCommentDao extends BaseMapper<TripComment> {
    List<TripComment> selectByTripId(@Param("tripId") Long tripId, @Param("limit") Integer limit);
    List<TripComment> selectByParentId(@Param("parentId") Long parentId);
    int countByTripId(@Param("tripId") Long tripId);
}