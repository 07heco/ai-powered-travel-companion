package com.travel.trip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.trip.entity.TripDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripDetailDao extends BaseMapper<TripDetail> {
}