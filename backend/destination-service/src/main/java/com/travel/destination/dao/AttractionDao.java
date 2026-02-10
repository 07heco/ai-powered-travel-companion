package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.destination.entity.Attraction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionDao extends BaseMapper<Attraction> {
}
