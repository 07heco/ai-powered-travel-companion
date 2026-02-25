package com.travel.trip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.trip.entity.TripComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripCommentDao extends BaseMapper<TripComment> {
}