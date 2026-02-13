package com.travel.booking.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.booking.entity.Booking;
import com.travel.booking.entity.Order;
import com.travel.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import com.travel.booking.service.FlightSearchService;
import com.travel.booking.service.HotelSearchService;
import com.travel.booking.service.TicketSearchService;

@RestController
@RequestMapping("/booking")
@Tag(name = "预订服务", description = "提供预订和订单管理相关接口")
public class BookingController {

    @Resource
    private BookingService bookingService;
    
    @Resource
    private FlightSearchService flightSearchService;
    
    @Resource
    private HotelSearchService hotelSearchService;
    
    @Resource
    private TicketSearchService ticketSearchService;

    @Operation(summary = "创建预订", description = "创建新的预订")
    @PostMapping
    public R<?> createBooking(@RequestBody Booking booking) {
        return R.success(bookingService.createBooking(booking));
    }

    @Operation(summary = "更新预订", description = "更新预订信息")
    @PutMapping
    public R<?> updateBooking(@RequestBody Booking booking) {
        return R.success(bookingService.updateBooking(booking));
    }

    @Operation(summary = "获取预订详情", description = "根据ID获取预订详情")
    @GetMapping("/{id}")
    public R<?> getBookingById(@PathVariable Long id) {
        return R.success(bookingService.getBookingById(id));
    }

    @Operation(summary = "删除预订", description = "根据ID删除预订")
    @DeleteMapping("/{id}")
    public R<?> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return R.success();
    }

    @Operation(summary = "获取用户预订列表", description = "分页获取用户预订列表")
    @GetMapping("/user/{userId}")
    public R<?> getUserBookings(@PathVariable Long userId, PageQuery query) {
        return R.success(bookingService.getUserBookings(userId, query));
    }

    @Operation(summary = "创建订单", description = "创建新的订单")
    @PostMapping("/order")
    public R<?> createOrder(@RequestBody Order order) {
        return R.success(bookingService.createOrder(order));
    }

    @Operation(summary = "获取订单详情", description = "根据ID获取订单详情")
    @GetMapping("/order/{id}")
    public R<?> getOrderById(@PathVariable Long id) {
        return R.success(bookingService.getOrderById(id));
    }

    @Operation(summary = "根据订单号获取订单", description = "根据订单号获取订单详情")
    @GetMapping("/order/no/{orderNo}")
    public R<?> getOrderByNo(@PathVariable String orderNo) {
        return R.success(bookingService.getOrderByNo(orderNo));
    }

    @Operation(summary = "获取用户订单列表", description = "分页获取用户订单列表")
    @GetMapping("/order/user/{userId}")
    public R<?> getUserOrders(@PathVariable Long userId, PageQuery query) {
        return R.success(bookingService.getUserOrders(userId, query));
    }

    @Operation(summary = "更新订单状态", description = "更新订单状态")
    @PutMapping("/order/{id}/status")
    public R<?> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        bookingService.updateOrderStatus(id, status);
        return R.success();
    }

    @Operation(summary = "支付回调", description = "处理支付回调")
    @PostMapping("/callback/pay")
    public R<?> payCallback(@RequestBody Map<String, Object> callbackData) {
        bookingService.handlePayCallback(callbackData);
        return R.success();
    }

    @Operation(summary = "搜索航班", description = "搜索航班")
    @PostMapping("/search/flight")
    public R<?> searchFlights(@RequestBody Map<String, Object> searchParams) {
        return R.success(flightSearchService.searchFlights(searchParams));
    }

    @Operation(summary = "搜索酒店", description = "搜索酒店")
    @PostMapping("/search/hotel")
    public R<?> searchHotels(@RequestBody Map<String, Object> searchParams) {
        return R.success(hotelSearchService.searchHotels(searchParams));
    }

    @Operation(summary = "搜索门票", description = "搜索门票")
    @PostMapping("/search/ticket")
    public R<?> searchTickets(@RequestBody Map<String, Object> searchParams) {
        return R.success(ticketSearchService.searchTickets(searchParams));
    }
}
