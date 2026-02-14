package com.travel.booking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.booking.entity.TicketBooking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketBookingDao extends BaseMapper<TicketBooking> {
}