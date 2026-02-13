package com.travel.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.booking.dao.*;
import com.travel.booking.entity.*;
import com.travel.booking.service.BookingService;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.travel.booking.utils.DistributeID;
import com.travel.booking.utils.WorkerIdHolder;

@Service
public class BookingServiceImpl implements BookingService {

    @Resource
    private BookingDao bookingDao;
    
    @Resource
    private OrderDao orderDao;
    
    @Resource
    private FlightBookingDao flightBookingDao;
    
    @Resource
    private HotelBookingDao hotelBookingDao;
    
    @Resource
    private TicketBookingDao ticketBookingDao;
    
    @Resource
    private DistributedLockUtil distributedLockUtil;

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        booking.setStatus("pending");
        booking.setPaymentStatus("unpaid");
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        bookingDao.insert(booking);
        return booking;
    }

    @Override
    @Transactional
    public Booking updateBooking(Booking booking) {
        booking.setUpdatedAt(LocalDateTime.now());
        bookingDao.updateById(booking);
        return booking;
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingDao.selectById(id);
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        bookingDao.deleteById(id);
    }

    @Override
    public PageResult<Booking> getUserBookings(Long userId, PageQuery query) {
        Page<Booking> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getUserId, userId);
        Page<Booking> result = bookingDao.selectPage(page, wrapper);
        return new PageResult<>(result.getTotal(), Math.toIntExact(result.getPages()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        String lockKey = "order:create:" + order.getUserId() + ":" + order.getBookingId();
        try {
            if (distributedLockUtil.tryLock(lockKey, 5, 10)) {
                order.setOrderNo(generateOrderNo());
                order.setStatus("pending");
                order.setCreatedAt(LocalDateTime.now());
                order.setUpdatedAt(LocalDateTime.now());
                orderDao.insert(order);
                return order;
            } else {
                throw new RuntimeException("创建订单失败，请稍后重试");
            }
        } finally {
            distributedLockUtil.unlock(lockKey);
        }
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
        return new PageResult<>(result.getTotal(), Math.toIntExact(result.getPages()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderDao.selectById(orderId);
        if (order != null) {
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            orderDao.updateById(order);
        }
    }

    @Override
    @Transactional
    public void handlePayCallback(Map<String, Object> callbackData) {
        String orderNo = (String) callbackData.get("orderNo");
        String payStatus = (String) callbackData.get("status");
        String transactionId = (String) callbackData.get("transactionId");
        
        Order order = getOrderByNo(orderNo);
        if (order != null) {
            order.setStatus("paid");
            order.setTransactionId(transactionId);
            order.setPaymentMethod((String) callbackData.get("paymentMethod"));
            order.setUpdatedAt(LocalDateTime.now());
            orderDao.updateById(order);
            
            Booking booking = getBookingById(order.getBookingId());
            if (booking != null) {
                booking.setPaymentStatus("paid");
                booking.setStatus("confirmed");
                booking.setUpdatedAt(LocalDateTime.now());
                bookingDao.updateById(booking);
            }
        }
    }

    // 生成订单号
    private String generateOrderNo() {
        return DistributeID.generateOrderNo(WorkerIdHolder.WORKER_ID);
    }
}
