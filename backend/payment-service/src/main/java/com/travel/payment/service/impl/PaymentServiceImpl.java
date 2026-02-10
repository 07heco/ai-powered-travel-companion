package com.travel.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.payment.dao.PaymentDao;
import com.travel.payment.entity.Payment;
import com.travel.payment.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public Payment createPayment(Payment payment) {
        if (payment.getPaymentNo() == null) {
            payment.setPaymentNo(generatePaymentNo());
        }
        paymentDao.insert(payment);
        return payment;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.selectById(id);
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getOrderId, orderId);
        return paymentDao.selectOne(wrapper);
    }

    @Override
    public Payment getPaymentByPaymentNo(String paymentNo) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getPaymentNo, paymentNo);
        return paymentDao.selectOne(wrapper);
    }

    @Override
    public PageResult<Payment> getUserPayments(Long userId, PageQuery query) {
        Page<Payment> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getUserId, userId);
        Page<Payment> result = paymentDao.selectPage(page, wrapper);
        return new PageResult<Payment>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public void updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentDao.selectById(paymentId);
        if (payment != null) {
            payment.setStatus(status);
            if ("SUCCESS".equals(status)) {
                payment.setPaymentTime(LocalDateTime.now());
            }
            paymentDao.updateById(payment);
        }
    }

    @Override
    public String processPayment(Long orderId, String paymentMethod, Double amount) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        payment.setPaymentNo(generatePaymentNo());
        paymentDao.insert(payment);
        return payment.getPaymentNo();
    }

    private String generatePaymentNo() {
        return "PAY" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
