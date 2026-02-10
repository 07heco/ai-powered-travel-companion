package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.destination.entity.Destination;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DestinationDao extends BaseMapper<Destination> {
}
