package com.travel.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.booking.dao.BookingDao;
import com.travel.booking.dao.OrderDao;
import com.travel.booking.entity.Booking;
import com.travel.booking.entity.Order;
import com.travel.booking.service.BookingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    @Resource
    private BookingDao bookingDao;

    @Resource
    private OrderDao orderDao;

    @Override
    public Booking createBooking(Booking booking) {
        bookingDao.insert(booking);
        return booking;
    }

    @Override
    public Booking updateBooking(Booking booking) {
        bookingDao.updateById(booking);
        return booking;
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingDao.selectById(id);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingDao.deleteById(id);
    }

    @Override
    public PageResult<Booking> getUserBookings(Long userId, PageQuery query) {
        Page<Booking> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getUserId, userId);
        Page<Booking> result = bookingDao.selectPage(page, wrapper);
        return new PageResult<Booking>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public Order createOrder(Order order) {
        if (order.getOrderNo() == null) {
            order.setOrderNo(generateOrderNo());
        }
        orderDao.insert(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.selectById(id);
    }

    @Override
    public Order getOrderByNo(String orderNo) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderNo, orderNo);
        return orderDao.selectOne(wrapper);
    }

    @Override
    public PageResult<Order> getUserOrders(Long userId, PageQuery query) {
        Page<Order> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        Page<Order> result = orderDao.selectPage(page, wrapper);
        return new PageResult<Order>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderDao.selectById(orderId);
        if (order != null) {
            order.setStatus(status);
            orderDao.updateById(order);
        }
    }

    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
