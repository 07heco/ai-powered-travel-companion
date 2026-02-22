package com.travel.ai.service.impl;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.ai.dao.AIPlanDao;
import com.travel.ai.dao.TravelMateDao;
import com.travel.ai.entity.AIPlan;
import com.travel.ai.entity.TravelMate;
import com.travel.ai.service.AIService;
import com.travel.ai.service.ModelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AIServiceImpl implements AIService {

    @Resource
    private AIPlanDao aiPlanDao;

    @Resource
    private TravelMateDao travelMateDao;

    @Resource
    private ModelService modelService;

    @Override
    public AIPlan generateAIPlan(Long userId, String destination, String startDate, String endDate, String preferences) {
        // 解析日期
        LocalDateTime start, end;
        if (startDate.length() == 10) { // 格式：yyyy-MM-dd
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            start = java.time.LocalDate.parse(startDate, dateFormatter).atStartOfDay();
            end = java.time.LocalDate.parse(endDate, dateFormatter).atStartOfDay();
        } else { // 格式：yyyy-MM-dd'T'HH:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            start = LocalDateTime.parse(startDate, formatter);
            end = LocalDateTime.parse(endDate, formatter);
        }
        long days = start.until(end, java.time.temporal.ChronoUnit.DAYS) + 1;

        // 使用AI生成旅行计划
        String prompt = "请为我生成一个详细的旅行计划，目的地是" + destination + "，" +
                "开始日期是" + startDate + "，结束日期是" + endDate + "，" +
                "我的偏好是" + preferences + "。" +
                "请包括每日行程安排、交通选择、预计费用和旅行建议。";

        String planContent = modelService.generateText(prompt);

        AIPlan aiPlan = new AIPlan();
        aiPlan.setUserId(userId);
        aiPlan.setPlanName(destination + " Travel Plan");
        aiPlan.setDestination(destination);
        aiPlan.setStartDate(start);
        aiPlan.setEndDate(end);
        aiPlan.setPlanContent(planContent);
        aiPlan.setStatus("COMPLETED");
        aiPlan.setCreatedAt(LocalDateTime.now());
        aiPlan.setUpdatedAt(LocalDateTime.now());

        return aiPlanDao.save(aiPlan);
    }

    @Override
    public AIPlan getAIPlanById(Long id) {
        return aiPlanDao.findById(id).orElse(null);
    }

    @Override
    public PageResult<AIPlan> getUserAIPlans(Long userId, PageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize());
        Page<AIPlan> result = aiPlanDao.findAll(pageRequest);
        return new PageResult<AIPlan>(result.getTotalElements(), result.getSize(), result.getNumber() + 1, result.getContent());
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

        return travelMateDao.save(travelMate);
    }

    @Override
    public TravelMate getTravelMateById(Long id) {
        return travelMateDao.findById(id).orElse(null);
    }

    @Override
    public java.util.List<TravelMate> getUserTravelMates(Long userId) {
        return travelMateDao.findAll();
    }

    @Override
    public void deleteTravelMate(Long id) {
        travelMateDao.deleteById(id);
    }

    @Override
    public String chatWithTravelMate(Long travelMateId, String message) {
        TravelMate travelMate = travelMateDao.findById(travelMateId).orElse(null);
        if (travelMate == null) {
            return "Travel mate not found";
        }

        // 使用AI生成回复
        String prompt = "你是一个名为" + travelMate.getName() + "的旅游助手，" +
                "你的个性是" + travelMate.getPersonality() + "，" +
                "你擅长的领域是" + travelMate.getExpertise() + "。" +
                "请根据以下用户消息给出友好、专业的旅游建议：" + message;

        return modelService.generateText(prompt);
    }
}
