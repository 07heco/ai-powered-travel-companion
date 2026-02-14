package com.travel.booking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.booking.entity.HotelBooking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelBookingDao extends BaseMapper<HotelBooking> {
}