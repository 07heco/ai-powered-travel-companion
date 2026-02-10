package com.travel.deals.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.deals.entity.Deal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DealDao extends BaseMapper<Deal> {
}
