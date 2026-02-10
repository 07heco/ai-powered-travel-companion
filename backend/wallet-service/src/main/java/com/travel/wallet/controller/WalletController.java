package com.travel.wallet.controller;

import com.travel.common.vo.R;
import com.travel.wallet.entity.Wallet;
import com.travel.wallet.entity.Coupon;
import com.travel.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wallet")
@Tag(name = "钱包服务", description = "提供钱包和优惠券相关接口")
public class WalletController {

    @Resource
    private WalletService walletService;

    @Operation(summary = "获取用户钱包", description = "根据用户ID获取钱包信息")
    @GetMapping("/user/{userId}")
    public R<?> getWalletByUserId(@PathVariable Long userId) {
        return R.success(walletService.getWalletByUserId(userId));
    }

    @Operation(summary = "充值钱包", description = "为用户钱包充值")
    @PostMapping("/recharge")
    public R<?> rechargeWallet(@RequestParam Long userId, @RequestParam Double amount) {
        walletService.rechargeWallet(userId, amount);
        return R.success();
    }

    @Operation(summary = "提现钱包", description = "从用户钱包提现")
    @PostMapping("/withdraw")
    public R<?> withdrawWallet(@RequestParam Long userId, @RequestParam Double amount) {
        walletService.withdrawWallet(userId, amount);
        return R.success();
    }

    @Operation(summary = "扣除钱包余额", description = "扣除用户钱包余额")
    @PostMapping("/deduct")
    public R<?> deductWallet(@RequestParam Long userId, @RequestParam Double amount) {
        walletService.deductWallet(userId, amount);
        return R.success();
    }

    @Operation(summary = "创建优惠券", description = "创建新的优惠券")
    @PostMapping("/coupon")
    public R<?> createCoupon(@RequestBody Coupon coupon) {
        return R.success(walletService.createCoupon(coupon));
    }

    @Operation(summary = "获取用户优惠券", description = "获取用户的可用优惠券列表")
    @GetMapping("/coupon/user/{userId}")
    public R<?> getUserCoupons(@PathVariable Long userId) {
        return R.success(walletService.getUserCoupons(userId));
    }

    @Operation(summary = "获取优惠券详情", description = "根据ID获取优惠券详情")
    @GetMapping("/coupon/{id}")
    public R<?> getCouponById(@PathVariable Long id) {
        return R.success(walletService.getCouponById(id));
    }

    @Operation(summary = "使用优惠券", description = "使用优惠券")
    @PutMapping("/coupon/{id}/use")
    public R<?> useCoupon(@PathVariable Long id) {
        walletService.useCoupon(id);
        return R.success();
    }

    @Operation(summary = "过期优惠券", description = "过期已失效的优惠券")
    @PostMapping("/coupon/expire")
    public R<?> expireCoupons() {
        walletService.expireCoupons();
        return R.success();
    }
}
