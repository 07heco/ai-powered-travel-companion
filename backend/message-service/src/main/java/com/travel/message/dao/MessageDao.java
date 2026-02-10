package com.travel.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDao extends BaseMapper<Message> {
}
