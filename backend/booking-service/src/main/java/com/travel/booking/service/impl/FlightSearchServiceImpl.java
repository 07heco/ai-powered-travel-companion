package com.travel.booking.service.impl;

import com.travel.booking.service.FlightSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FlightSearchServiceImpl implements FlightSearchService {
    @Override
    public List<Map<String, Object>> searchFlights(Map<String, Object> searchParams) {
        List<Map<String, Object>> flights = new ArrayList<>();
        
        // 模拟航班数据
        Map<String, Object> flight1 = Map.of(
            "flightNo", "CA1234",
            "airline", "中国国航",
            "departureAirport", "北京首都国际机场",
            "arrivalAirport", "上海浦东国际机场",
            "departureTime", "2024-03-01 09:00",
            "arrivalTime", "2024-03-01 11:30",
            "price", 1200.00,
            "classType", "经济舱"
        );
        
        Map<String, Object> flight2 = Map.of(
            "flightNo", "MU5678",
            "airline", "东方航空",
            "departureAirport", "北京首都国际机场",
            "arrivalAirport", "上海浦东国际机场",
            "departureTime", "2024-03-01 10:30",
            "arrivalTime", "2024-03-01 13:00",
            "price", 1100.00,
            "classType", "经济舱"
        );
        
        flights.add(flight1);
        flights.add(flight2);
        return flights;
    }
}
