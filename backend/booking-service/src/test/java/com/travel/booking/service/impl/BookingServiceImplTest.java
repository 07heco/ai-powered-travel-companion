package com.travel.booking.service.impl;

import com.travel.booking.dao.BookingDao;
import com.travel.booking.dao.OrderDao;
import com.travel.booking.entity.Booking;
import com.travel.booking.entity.Order;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    @Mock
    private BookingDao bookingDao;
    
    @Mock
    private OrderDao orderDao;
    
    @InjectMocks
    private BookingServiceImpl bookingService;
    
    @Test
    public void testCreateBooking() {
        Booking booking = new Booking();
        booking.setUserId(1L);
        booking.setBookingType("flight");
        booking.setBookingName("Test Booking");
        booking.setTotalPrice(1000.00);
        
        when(bookingDao.insert(any(Booking.class))).thenReturn(1);
        
        Booking result = bookingService.createBooking(booking);
        
        assertNotNull(result);
        assertEquals("pending", result.getStatus());
        assertEquals("unpaid", result.getPaymentStatus());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }
    
    @Test
    public void testUpdateBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setBookingType("flight");
        booking.setBookingName("Updated Booking");
        booking.setTotalPrice(1200.00);
        
        when(bookingDao.updateById(any(Booking.class))).thenReturn(1);
        
        Booking result = bookingService.updateBooking(booking);
        
        assertNotNull(result);
        assertEquals("Updated Booking", result.getBookingName());
        assertEquals(1200.00, result.getTotalPrice());
        assertNotNull(result.getUpdatedAt());
    }
    
    @Test
    public void testGetBookingById() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setBookingType("flight");
        
        when(bookingDao.selectById(1L)).thenReturn(booking);
        
        Booking result = bookingService.getBookingById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getUserId());
        assertEquals("flight", result.getBookingType());
    }
    
    @Test
    public void testDeleteBooking() {
        when(bookingDao.deleteById(1L)).thenReturn(1);
        
        bookingService.deleteBooking(1L);
        
        verify(bookingDao, times(1)).deleteById(1L);
    }
    
    @Test
    public void testGetUserBookings() {
        PageQuery query = new PageQuery();
        query.setPage(1);
        query.setSize(10);
        
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Booking> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 10);
        page.setTotal(1);
        page.setRecords(Collections.singletonList(new Booking()));
        
        when(bookingDao.selectPage(any(), any())).thenReturn(page);
        
        PageResult<Booking> result = bookingService.getUserBookings(1L, query);
        
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
    }
    
    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setUserId(1L);
        order.setBookingId(1L);
        order.setTotalAmount(1000.00);
        
        when(orderDao.insert(any(Order.class))).thenReturn(1);
        
        Order result = bookingService.createOrder(order);
        
        assertNotNull(result);
        assertNotNull(result.getOrderNo());
        assertEquals("pending", result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }
    
    @Test
    public void testHandlePayCallback() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderNo("ORD123456789");
        order.setUserId(1L);
        order.setBookingId(1L);
        order.setTotalAmount(1000.00);
        order.setStatus("pending");
        
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(1L);
        booking.setPaymentStatus("unpaid");
        booking.setStatus("pending");
        
        when(orderDao.selectOne(any())).thenReturn(order);
        when(orderDao.updateById(any(Order.class))).thenReturn(1);
        when(bookingDao.selectById(1L)).thenReturn(booking);
        when(bookingDao.updateById(any(Booking.class))).thenReturn(1);
        
        Map<String, Object> callbackData = Map.of(
            "orderNo", "ORD123456789",
            "status", "success",
            "transactionId", "TXN123456789",
            "paymentMethod", "alipay"
        );
        
        bookingService.handlePayCallback(callbackData);
        
        verify(orderDao, times(1)).updateById(any(Order.class));
        verify(bookingDao, times(1)).updateById(any(Booking.class));
    }
}
