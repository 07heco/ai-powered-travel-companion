package com.travel.booking.service;

import java.util.Map;
import java.util.List;

public interface HotelSearchService {
    List<Map<String, Object>> searchHotels(Map<String, Object> searchParams);
}
