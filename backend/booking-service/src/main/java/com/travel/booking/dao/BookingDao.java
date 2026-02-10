package com.travel.booking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.booking.entity.Booking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookingDao extends BaseMapper<Booking> {
}
