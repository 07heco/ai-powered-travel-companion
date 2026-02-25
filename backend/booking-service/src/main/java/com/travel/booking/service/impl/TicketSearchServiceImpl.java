package com.travel.booking.service.impl;

import com.travel.booking.service.TicketSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TicketSearchServiceImpl implements TicketSearchService {
    @Override
    public List<Map<String, Object>> searchTickets(Map<String, Object> searchParams) {
        List<Map<String, Object>> tickets = new ArrayList<>();
        
        // 模拟门票数据
        Map<String, Object> ticket1 = Map.of(
            "attractionName", "上海迪士尼度假区",
            "ticketType", "一日标准票",
            "price", 699.00,
            "visitDate", "2024-03-01",
            "validity", "当日有效"
        );
        
        Map<String, Object> ticket2 = Map.of(
            "attractionName", "上海环球影城",
            "ticketType", "一日标准票",
            "price", 599.00,
            "visitDate", "2024-03-01",
            "validity", "当日有效"
        );
        
        tickets.add(ticket1);
        tickets.add(ticket2);
        return tickets;
    }
}
