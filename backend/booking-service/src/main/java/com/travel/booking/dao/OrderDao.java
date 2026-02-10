package com.travel.booking.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.booking.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao extends BaseMapper<Order> {
}
