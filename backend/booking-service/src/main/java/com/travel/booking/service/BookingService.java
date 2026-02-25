package com.travel.booking.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.booking.entity.Booking;
import com.travel.booking.entity.Order;

import java.util.List;
import java.util.Map;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking updateBooking(Booking booking);
    Booking getBookingById(Long id);
    void deleteBooking(Long id);
    PageResult<Booking> getUserBookings(Long userId, PageQuery query);
    Order createOrder(Order order);
    Order getOrderById(Long id);
    Order getOrderByNo(String orderNo);
    PageResult<Order> getUserOrders(Long userId, PageQuery query);
    void updateOrderStatus(Long orderId, String status);
    void handlePayCallback(Map<String, Object> callbackData);
}
