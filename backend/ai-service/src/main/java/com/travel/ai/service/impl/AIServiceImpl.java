package com.travel.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.ai.dao.AIPlanDao;
import com.travel.ai.dao.TravelMateDao;
import com.travel.ai.entity.AIPlan;
import com.travel.ai.entity.TravelMate;
import com.travel.ai.service.AIService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class AIServiceImpl implements AIService {

    @Resource
    private AIPlanDao aiPlanDao;

    @Resource
    private TravelMateDao travelMateDao;

    // @Resource
    // private OpenAiChatClient chatClient;

    @Override
    public AIPlan generateAIPlan(Long userId, String destination, String startDate, String endDate, String preferences) {
        // 模拟AI生成的旅行计划
        String planContent = "AI-generated travel plan for " + destination + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate + "\n" +
                "Preferences: " + preferences + "\n" +
                "Day 1: Arrival and city tour\n" +
                "Day 2: Main attractions\n" +
                "Day 3: Local experiences\n" +
                "Day 4: Shopping and departure";

        AIPlan aiPlan = new AIPlan();
        aiPlan.setUserId(userId);
        aiPlan.setPlanName(destination + " Travel Plan");
        aiPlan.setDestination(destination);
        aiPlan.setStartDate(LocalDateTime.parse(startDate));
        aiPlan.setEndDate(LocalDateTime.parse(endDate));
        aiPlan.setPlanContent(planContent);
        aiPlan.setStatus("COMPLETED");
        aiPlan.setCreatedAt(LocalDateTime.now());
        aiPlan.setUpdatedAt(LocalDateTime.now());

        aiPlanDao.insert(aiPlan);
        return aiPlan;
    }

    @Override
    public AIPlan getAIPlanById(Long id) {
        return aiPlanDao.selectById(id);
    }

    @Override
    public PageResult<AIPlan> getUserAIPlans(Long userId, PageQuery query) {
        Page<AIPlan> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<AIPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AIPlan::getUserId, userId);
        Page<AIPlan> result = aiPlanDao.selectPage(page, wrapper);
        return new PageResult<AIPlan>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public void deleteAIPlan(Long id) {
        aiPlanDao.deleteById(id);
    }

    @Override
    public TravelMate createTravelMate(Long userId, String name, String personality, String expertise) {
        TravelMate travelMate = new TravelMate();
        travelMate.setUserId(userId);
        travelMate.setName(name);
        travelMate.setPersonality(personality);
        travelMate.setExpertise(expertise);
        travelMate.setStatus("ACTIVE");
        travelMate.setCreatedAt(LocalDateTime.now());
        travelMate.setUpdatedAt(LocalDateTime.now());

        travelMateDao.insert(travelMate);
        return travelMate;
    }

    @Override
    public TravelMate getTravelMateById(Long id) {
        return travelMateDao.selectById(id);
    }

    @Override
    public java.util.List<TravelMate> getUserTravelMates(Long userId) {
        LambdaQueryWrapper<TravelMate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TravelMate::getUserId, userId);
        return travelMateDao.selectList(wrapper);
    }

    @Override
    public void deleteTravelMate(Long id) {
        travelMateDao.deleteById(id);
    }

    @Override
    public String chatWithTravelMate(Long travelMateId, String message) {
        TravelMate travelMate = travelMateDao.selectById(travelMateId);
        if (travelMate == null) {
            return "Travel mate not found";
        }

        // 模拟旅游伙伴的回复
        return "Hello! I'm " + travelMate.getName() + ". " +
                "I have a " + travelMate.getPersonality() + " personality and expertise in " + travelMate.getExpertise() + ". " +
                "I'd be happy to help with your travel plans!";
    }
}
