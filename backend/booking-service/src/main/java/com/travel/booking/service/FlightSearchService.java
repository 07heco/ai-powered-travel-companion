package com.travel.booking.service;

import java.util.Map;
import java.util.List;

public interface FlightSearchService {
    List<Map<String, Object>> searchFlights(Map<String, Object> searchParams);
}
