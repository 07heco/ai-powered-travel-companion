package com.travel.payment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.payment.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentDao extends BaseMapper<Payment> {
}
