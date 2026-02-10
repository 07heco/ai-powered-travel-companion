package com.travel.payment.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.payment.entity.Payment;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPaymentById(Long id);
    Payment getPaymentByOrderId(Long orderId);
    Payment getPaymentByPaymentNo(String paymentNo);
    PageResult<Payment> getUserPayments(Long userId, PageQuery query);
    void updatePaymentStatus(Long paymentId, String status);
    String processPayment(Long orderId, String paymentMethod, Double amount);
}
