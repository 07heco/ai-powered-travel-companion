package com.travel.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.user.entity.User;

/**
 * 用户DAO接口
 */
public interface UserDao extends BaseMapper<User> {
    
    /**
     * 根据手机号查询用户
     */
    User selectByPhone(String phone);
    
    /**
     * 根据邮箱查询用户
     */
    User selectByEmail(String email);
}
