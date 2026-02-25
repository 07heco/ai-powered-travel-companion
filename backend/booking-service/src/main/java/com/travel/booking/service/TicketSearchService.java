package com.travel.booking.service;

import java.util.Map;
import java.util.List;

public interface TicketSearchService {
    List<Map<String, Object>> searchTickets(Map<String, Object> searchParams);
}
