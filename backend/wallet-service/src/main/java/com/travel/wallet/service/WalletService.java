package com.travel.wallet.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.wallet.entity.Wallet;
import com.travel.wallet.entity.Coupon;

import java.util.List;

public interface WalletService {
    Wallet getWalletByUserId(Long userId);
    void rechargeWallet(Long userId, Double amount);
    void withdrawWallet(Long userId, Double amount);
    void deductWallet(Long userId, Double amount);
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getUserCoupons(Long userId);
    Coupon getCouponById(Long id);
    void useCoupon(Long couponId);
    void expireCoupons();
}
