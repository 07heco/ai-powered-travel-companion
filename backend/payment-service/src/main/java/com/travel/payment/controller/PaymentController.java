package com.travel.payment.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.payment.entity.Payment;
import com.travel.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/payment")
@Tag(name = "支付服务", description = "提供支付相关接口")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Operation(summary = "创建支付", description = "创建新的支付记录")
    @PostMapping
    public R<?> createPayment(@RequestBody Payment payment) {
        return R.success(paymentService.createPayment(payment));
    }

    @Operation(summary = "获取支付详情", description = "根据ID获取支付详情")
    @GetMapping("/{id}")
    public R<?> getPaymentById(@PathVariable Long id) {
        return R.success(paymentService.getPaymentById(id));
    }

    @Operation(summary = "根据订单ID获取支付", description = "根据订单ID获取支付详情")
    @GetMapping("/order/{orderId}")
    public R<?> getPaymentByOrderId(@PathVariable Long orderId) {
        return R.success(paymentService.getPaymentByOrderId(orderId));
    }

    @Operation(summary = "根据支付单号获取支付", description = "根据支付单号获取支付详情")
    @GetMapping("/no/{paymentNo}")
    public R<?> getPaymentByPaymentNo(@PathVariable String paymentNo) {
        return R.success(paymentService.getPaymentByPaymentNo(paymentNo));
    }

    @Operation(summary = "获取用户支付记录", description = "分页获取用户支付记录")
    @GetMapping("/user/{userId}")
    public R<?> getUserPayments(@PathVariable Long userId, PageQuery query) {
        return R.success(paymentService.getUserPayments(userId, query));
    }

    @Operation(summary = "更新支付状态", description = "更新支付状态")
    @PutMapping("/{id}/status")
    public R<?> updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
        paymentService.updatePaymentStatus(id, status);
        return R.success();
    }

    @Operation(summary = "处理支付", description = "处理支付请求")
    @PostMapping("/process")
    public R<?> processPayment(@RequestParam Long orderId, @RequestParam String paymentMethod, @RequestParam Double amount) {
        String paymentNo = paymentService.processPayment(orderId, paymentMethod, amount);
        return R.success(paymentNo);
    }
}
