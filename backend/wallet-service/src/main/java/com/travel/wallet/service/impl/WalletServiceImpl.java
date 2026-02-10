package com.travel.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.travel.wallet.dao.WalletDao;
import com.travel.wallet.dao.CouponDao;
import com.travel.wallet.entity.Wallet;
import com.travel.wallet.entity.Coupon;
import com.travel.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Resource
    private WalletDao walletDao;

    @Resource
    private CouponDao couponDao;

    @Override
    public Wallet getWalletByUserId(Long userId) {
        LambdaQueryWrapper<Wallet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Wallet::getUserId, userId);
        Wallet wallet = walletDao.selectOne(wrapper);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setBalance(0.0);
            wallet.setCreatedAt(LocalDateTime.now());
            wallet.setUpdatedAt(LocalDateTime.now());
            walletDao.insert(wallet);
        }
        return wallet;
    }

    @Override
    public void rechargeWallet(Long userId, Double amount) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.setBalance(wallet.getBalance() + amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletDao.updateById(wallet);
    }

    @Override
    public void withdrawWallet(Long userId, Double amount) {
        Wallet wallet = getWalletByUserId(userId);
        if (wallet.getBalance() >= amount) {
            wallet.setBalance(wallet.getBalance() - amount);
            wallet.setUpdatedAt(LocalDateTime.now());
            walletDao.updateById(wallet);
        } else {
            throw new RuntimeException("Insufficient balance");
        }
    }

    @Override
    public void deductWallet(Long userId, Double amount) {
        withdrawWallet(userId, amount);
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        coupon.setCreatedAt(LocalDateTime.now());
        coupon.setUpdatedAt(LocalDateTime.now());
        couponDao.insert(coupon);
        return coupon;
    }

    @Override
    public List<Coupon> getUserCoupons(Long userId) {
        LambdaQueryWrapper<Coupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Coupon::getUserId, userId);
        wrapper.eq(Coupon::getStatus, "ACTIVE");
        wrapper.gt(Coupon::getEndDate, LocalDateTime.now());
        return couponDao.selectList(wrapper);
    }

    @Override
    public Coupon getCouponById(Long id) {
        return couponDao.selectById(id);
    }

    @Override
    public void useCoupon(Long couponId) {
        Coupon coupon = couponDao.selectById(couponId);
        if (coupon != null) {
            coupon.setStatus("USED");
            coupon.setUpdatedAt(LocalDateTime.now());
            couponDao.updateById(coupon);
        }
    }

    @Override
    public void expireCoupons() {
        LambdaQueryWrapper<Coupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Coupon::getStatus, "ACTIVE");
        wrapper.lt(Coupon::getEndDate, LocalDateTime.now());
        List<Coupon> coupons = couponDao.selectList(wrapper);
        for (Coupon coupon : coupons) {
            coupon.setStatus("EXPIRED");
            coupon.setUpdatedAt(LocalDateTime.now());
            couponDao.updateById(coupon);
        }
    }
}
