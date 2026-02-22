package com.travel.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.user.entity.UserThirdParty;

/**
 * 第三方账号关联数据访问接口
 */
public interface UserThirdPartyDao extends BaseMapper<UserThirdParty> {
    
    /**
     * 根据第三方类型和openid查询
     */
    UserThirdParty selectByThirdTypeAndOpenid(String thirdType, String thirdOpenid);
    
    /**
     * 根据第三方类型和unionid查询
     */
    UserThirdParty selectByThirdTypeAndUnionid(String thirdType, String thirdUnionid);
    
    /**
     * 根据用户ID和第三方类型查询
     */
    UserThirdParty selectByUserIdAndThirdType(Long userId, String thirdType);
}
