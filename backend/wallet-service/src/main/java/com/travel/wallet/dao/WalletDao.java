package com.travel.wallet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.wallet.entity.Wallet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletDao extends BaseMapper<Wallet> {
}
