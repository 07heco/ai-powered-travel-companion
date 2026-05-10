package com.travel.ai.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.ai.entity.AIPlan;
import com.travel.ai.entity.TravelMate;

import java.util.List;

public interface AIService {
    AIPlan generateAIPlan(Long userId, String destination, String startDate, String endDate, String preferences);
    AIPlan getAIPlanById(Long id);
    PageResult<AIPlan> getUserAIPlans(Long userId, PageQuery query);
    void deleteAIPlan(Long id);
    TravelMate createTravelMate(Long userId, String name, String personality, String expertise);
    TravelMate getTravelMateById(Long id);
    List<TravelMate> getUserTravelMates(Long userId);
    void deleteTravelMate(Long id);
    String chatWithTravelMate(Long travelMateId, String message);
}
