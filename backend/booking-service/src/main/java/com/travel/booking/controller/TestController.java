package com.travel.booking.controller;

import com.travel.common.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/test")
    public R<?> index() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "预订服务 API 接口");
        data.put("status", "success");
        data.put("version", "1.0.0");
        data.put("loginInfo", "已登录成功，当前用户: root");
        
        Map<String, Object> apiInfo = new HashMap<>();
        apiInfo.put("bookingAPI", "/booking/api/v1/booking");
        apiInfo.put("orderAPI", "/booking/api/v1/booking/order");
        apiInfo.put("paymentCallback", "/booking/api/v1/booking/callback/pay");
        apiInfo.put("testAPI", "/booking/test/hello");
        
        data.put("apiInfo", apiInfo);
        
        return R.success(data);
    }
    
    @GetMapping("/test/hello")
    public R<?> hello() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "预订服务测试接口");
        data.put("status", "success");
        return R.success(data);
    }
    
    @GetMapping("/test/booking/list")
    public R<?> getBookingList() {
        Map<String, Object> booking1 = new HashMap<>();
        booking1.put("id", 1);
        booking1.put("userId", 1);
        booking1.put("bookingType", "flight");
        booking1.put("bookingName", "北京到上海航班");
        booking1.put("totalPrice", 1200.00);
        booking1.put("status", "confirmed");
        booking1.put("paymentStatus", "paid");
        
        Map<String, Object> booking2 = new HashMap<>();
        booking2.put("id", 2);
        booking2.put("userId", 1);
        booking2.put("bookingType", "hotel");
        booking2.put("bookingName", "上海浦东香格里拉大酒店");
        booking2.put("totalPrice", 1800.00);
        booking2.put("status", "confirmed");
        booking2.put("paymentStatus", "paid");
        
        return R.success(new Object[]{booking1, booking2});
    }
    
    @GetMapping("/test/order/list")
    public R<?> getOrderList() {
        Map<String, Object> order1 = new HashMap<>();
        order1.put("id", 1);
        order1.put("orderNo", "ORD123456789");
        order1.put("userId", 1);
        order1.put("bookingId", 1);
        order1.put("totalAmount", 1200.00);
        order1.put("status", "paid");
        order1.put("paymentMethod", "alipay");
        
        return R.success(new Object[]{order1});
    }
}
