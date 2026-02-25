package com.travel.booking.service.impl;

import com.travel.booking.service.HotelSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HotelSearchServiceImpl implements HotelSearchService {
    @Override
    public List<Map<String, Object>> searchHotels(Map<String, Object> searchParams) {
        List<Map<String, Object>> hotels = new ArrayList<>();
        
        // 模拟酒店数据
        Map<String, Object> hotel1 = Map.of(
            "hotelName", "上海浦东香格里拉大酒店",
            "hotelAddress", "上海市浦东新区富城路33号",
            "price", 1800.00,
            "roomType", "豪华大床房",
            "rating", 4.8,
            "facilities", List.of("免费WiFi", "健身房", "游泳池", "停车场")
        );
        
        Map<String, Object> hotel2 = Map.of(
            "hotelName", "上海外滩华尔道夫酒店",
            "hotelAddress", "上海市黄浦区中山东一路2号",
            "price", 2200.00,
            "roomType", "豪华江景房",
            "rating", 4.9,
            "facilities", List.of("免费WiFi", "健身房", "SPA", "停车场")
        );
        
        hotels.add(hotel1);
        hotels.add(hotel2);
        return hotels;
    }
}
