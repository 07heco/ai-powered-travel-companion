package com.travel.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.booking.dao.*;
import com.travel.booking.entity.*;
import com.travel.booking.service.BookingService;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

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

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        logger.info("创建预订开始，用户ID: {}, 预订类型: {}", booking.getUserId(), booking.getBookingType());
        try {
            booking.setStatus("pending");
            booking.setPaymentStatus("unpaid");
            booking.setCreatedAt(LocalDateTime.now());
            booking.setUpdatedAt(LocalDateTime.now());
            
            // 处理临时字段
            if (booking.getPrice() != null) {
                booking.setTotalPrice(booking.getPrice());
            }
            
            // 设置默认的预订名称
            if (booking.getBookingName() == null || booking.getBookingName().isEmpty()) {
                booking.setBookingName(booking.getBookingType() + " 预订");
            }
            
            bookingDao.insert(booking);
            logger.info("创建预订成功，预订ID: {}", booking.getId());
            return booking;
        } catch (Exception e) {
            logger.error("创建预订失败，用户ID: {}, 错误信息: {}", booking.getUserId(), e.getMessage(), e);
            throw new RuntimeException("创建预订失败，请稍后重试", e);
        }
    }

    @Override
    @Transactional
    public Booking updateBooking(Booking booking) {
        logger.info("更新预订开始，预订ID: {}", booking.getId());
        try {
            booking.setUpdatedAt(LocalDateTime.now());
            bookingDao.updateById(booking);
            logger.info("更新预订成功，预订ID: {}", booking.getId());
            return booking;
        } catch (Exception e) {
            logger.error("更新预订失败，预订ID: {}, 错误信息: {}", booking.getId(), e.getMessage(), e);
            throw new RuntimeException("更新预订失败，请稍后重试", e);
        }
    }

    @Override
    public Booking getBookingById(Long id) {
        logger.info("获取预订详情，预订ID: {}", id);
        try {
            Booking booking = bookingDao.selectById(id);
            if (booking == null) {
                logger.warn("预订不存在，预订ID: {}", id);
            }
            return booking;
        } catch (Exception e) {
            logger.error("获取预订详情失败，预订ID: {}, 错误信息: {}", id, e.getMessage(), e);
            throw new RuntimeException("获取预订详情失败，请稍后重试", e);
        }
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        logger.info("删除预订开始，预订ID: {}", id);
        try {
            bookingDao.deleteById(id);
            logger.info("删除预订成功，预订ID: {}", id);
        } catch (Exception e) {
            logger.error("删除预订失败，预订ID: {}, 错误信息: {}", id, e.getMessage(), e);
            throw new RuntimeException("删除预订失败，请稍后重试", e);
        }
    }

    @Override
    public PageResult<Booking> getUserBookings(Long userId, PageQuery query) {
        logger.info("获取用户预订列表，用户ID: {}, 页码: {}, 每页大小: {}", userId, query.getPage(), query.getSize());
        try {
            Page<Booking> page = new Page<>(query.getPage(), query.getSize());
            LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Booking::getUserId, userId);
            Page<Booking> result = bookingDao.selectPage(page, wrapper);
            PageResult<Booking> pageResult = new PageResult<>(result.getTotal(), Math.toIntExact(result.getPages()), Math.toIntExact(result.getCurrent()), result.getRecords());
            logger.info("获取用户预订列表成功，用户ID: {}, 总记录数: {}", userId, pageResult.getTotal());
            return pageResult;
        } catch (Exception e) {
            logger.error("获取用户预订列表失败，用户ID: {}, 错误信息: {}", userId, e.getMessage(), e);
            throw new RuntimeException("获取用户预订列表失败，请稍后重试", e);
        }
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        logger.info("创建订单开始，用户ID: {}, 预订ID: {}", order.getUserId(), order.getBookingId());
        try {
            order.setOrderNo(generateOrderNo());
            order.setStatus("pending");
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            orderDao.insert(order);
            logger.info("创建订单成功，订单号: {}, 订单ID: {}", order.getOrderNo(), order.getId());
            return order;
        } catch (Exception e) {
            logger.error("创建订单失败，用户ID: {}, 预订ID: {}, 错误信息: {}", order.getUserId(), order.getBookingId(), e.getMessage(), e);
            throw new RuntimeException("创建订单失败，请稍后重试", e);
        }
    }

    @Override
    public Order getOrderById(Long id) {
        logger.info("获取订单详情，订单ID: {}", id);
        try {
            Order order = orderDao.selectById(id);
            if (order == null) {
                logger.warn("订单不存在，订单ID: {}", id);
            }
            return order;
        } catch (Exception e) {
            logger.error("获取订单详情失败，订单ID: {}, 错误信息: {}", id, e.getMessage(), e);
            throw new RuntimeException("获取订单详情失败，请稍后重试", e);
        }
    }

    @Override
    public Order getOrderByNo(String orderNo) {
        logger.info("根据订单号获取订单，订单号: {}", orderNo);
        try {
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getOrderNo, orderNo);
            Order order = orderDao.selectOne(wrapper);
            if (order == null) {
                logger.warn("订单不存在，订单号: {}", orderNo);
            }
            return order;
        } catch (Exception e) {
            logger.error("根据订单号获取订单失败，订单号: {}, 错误信息: {}", orderNo, e.getMessage(), e);
            throw new RuntimeException("根据订单号获取订单失败，请稍后重试", e);
        }
    }

    @Override
    public PageResult<Order> getUserOrders(Long userId, PageQuery query) {
        logger.info("获取用户订单列表，用户ID: {}, 页码: {}, 每页大小: {}", userId, query.getPage(), query.getSize());
        try {
            Page<Order> page = new Page<>(query.getPage(), query.getSize());
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getUserId, userId);
            Page<Order> result = orderDao.selectPage(page, wrapper);
            PageResult<Order> pageResult = new PageResult<>(result.getTotal(), Math.toIntExact(result.getPages()), Math.toIntExact(result.getCurrent()), result.getRecords());
            logger.info("获取用户订单列表成功，用户ID: {}, 总记录数: {}", userId, pageResult.getTotal());
            return pageResult;
        } catch (Exception e) {
            logger.error("获取用户订单列表失败，用户ID: {}, 错误信息: {}", userId, e.getMessage(), e);
            throw new RuntimeException("获取用户订单列表失败，请稍后重试", e);
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        logger.info("更新订单状态，订单ID: {}, 新状态: {}", orderId, status);
        try {
            Order order = orderDao.selectById(orderId);
            if (order != null) {
                order.setStatus(status);
                order.setUpdatedAt(LocalDateTime.now());
                orderDao.updateById(order);
                logger.info("更新订单状态成功，订单ID: {}, 新状态: {}", orderId, status);
            } else {
                logger.warn("订单不存在，订单ID: {}", orderId);
                throw new RuntimeException("订单不存在，无法更新状态");
            }
        } catch (RuntimeException e) {
            logger.error("更新订单状态失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("更新订单状态失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
            throw new RuntimeException("更新订单状态失败，请稍后重试", e);
        }
    }

    @Override
    @Transactional
    public void handlePayCallback(Map<String, Object> callbackData) {
        String orderNo = (String) callbackData.get("orderNo");
        String payStatus = (String) callbackData.get("status");
        String transactionId = (String) callbackData.get("transactionId");
        String paymentMethod = (String) callbackData.get("paymentMethod");
        
        logger.info("处理支付回调，订单号: {}, 支付状态: {}, 交易ID: {}, 支付方式: {}", orderNo, payStatus, transactionId, paymentMethod);
        try {
            if (orderNo == null) {
                logger.error("支付回调缺少订单号");
                throw new RuntimeException("支付回调缺少订单号");
            }
            
            Order order = getOrderByNo(orderNo);
            if (order != null) {
                logger.info("找到订单，订单ID: {}, 当前状态: {}", order.getId(), order.getStatus());
                
                // 检查订单状态，避免重复处理
                if ("paid".equals(order.getStatus())) {
                    logger.warn("订单已支付，无需重复处理，订单号: {}", orderNo);
                    return;
                }
                
                order.setStatus("paid");
                order.setTransactionId(transactionId);
                order.setPaymentMethod(paymentMethod);
                order.setUpdatedAt(LocalDateTime.now());
                orderDao.updateById(order);
                logger.info("更新订单状态成功，订单ID: {}, 新状态: paid", order.getId());
                
                Booking booking = getBookingById(order.getBookingId());
                if (booking != null) {
                    logger.info("找到关联预订，预订ID: {}, 当前状态: {}, 支付状态: {}", booking.getId(), booking.getStatus(), booking.getPaymentStatus());
                    booking.setPaymentStatus("paid");
                    booking.setStatus("confirmed");
                    booking.setUpdatedAt(LocalDateTime.now());
                    bookingDao.updateById(booking);
                    logger.info("更新预订状态成功，预订ID: {}, 新状态: confirmed, 支付状态: paid", booking.getId());
                } else {
                    logger.warn("订单关联的预订不存在，订单ID: {}, 预订ID: {}", order.getId(), order.getBookingId());
                }
            } else {
                logger.warn("订单不存在，订单号: {}", orderNo);
                // 订单不存在时，返回而不抛出异常
                return;
            }
        } catch (RuntimeException e) {
            logger.error("处理支付回调失败，订单号: {}, 错误信息: {}", orderNo, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("处理支付回调失败，订单号: {}, 错误信息: {}", orderNo, e.getMessage(), e);
            throw new RuntimeException("处理支付回调失败，请稍后重试", e);
        }
    }

    // 生成订单号
    private String generateOrderNo() {
        return "ORDER" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

