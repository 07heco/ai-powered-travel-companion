package com.travel.booking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.booking.entity.FlightBooking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlightBookingDao extends BaseMapper<FlightBooking> {
}