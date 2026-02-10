package com.travel.ai.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.ai.entity.AIPlan;
import com.travel.ai.entity.TravelMate;
import com.travel.ai.service.AIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ai")
@Tag(name = "AI服务", description = "提供智能旅游助手相关接口")
public class AIController {

    @Resource
    private AIService aiService;

    @Operation(summary = "生成AI旅行计划", description = "根据目的地、日期和偏好生成AI旅行计划")
    @PostMapping("/plan/generate")
    public R<?> generateAIPlan(@RequestParam Long userId, @RequestParam String destination, 
                              @RequestParam String startDate, @RequestParam String endDate, 
                              @RequestParam String preferences) {
        return R.success(aiService.generateAIPlan(userId, destination, startDate, endDate, preferences));
    }

    @Operation(summary = "获取AI计划详情", description = "根据ID获取AI计划详情")
    @GetMapping("/plan/{id}")
    public R<?> getAIPlanById(@PathVariable Long id) {
        return R.success(aiService.getAIPlanById(id));
    }

    @Operation(summary = "获取用户AI计划列表", description = "分页获取用户AI计划列表")
    @GetMapping("/plan/user/{userId}")
    public R<?> getUserAIPlans(@PathVariable Long userId, PageQuery query) {
        return R.success(aiService.getUserAIPlans(userId, query));
    }

    @Operation(summary = "删除AI计划", description = "根据ID删除AI计划")
    @DeleteMapping("/plan/{id}")
    public R<?> deleteAIPlan(@PathVariable Long id) {
        aiService.deleteAIPlan(id);
        return R.success();
    }

    @Operation(summary = "创建旅游伙伴", description = "创建新的旅游伙伴")
    @PostMapping("/mate/create")
    public R<?> createTravelMate(@RequestParam Long userId, @RequestParam String name, 
                                @RequestParam String personality, @RequestParam String expertise) {
        return R.success(aiService.createTravelMate(userId, name, personality, expertise));
    }

    @Operation(summary = "获取旅游伙伴详情", description = "根据ID获取旅游伙伴详情")
    @GetMapping("/mate/{id}")
    public R<?> getTravelMateById(@PathVariable Long id) {
        return R.success(aiService.getTravelMateById(id));
    }

    @Operation(summary = "获取用户旅游伙伴列表", description = "获取用户的旅游伙伴列表")
    @GetMapping("/mate/user/{userId}")
    public R<?> getUserTravelMates(@PathVariable Long userId) {
        return R.success(aiService.getUserTravelMates(userId));
    }

    @Operation(summary = "删除旅游伙伴", description = "根据ID删除旅游伙伴")
    @DeleteMapping("/mate/{id}")
    public R<?> deleteTravelMate(@PathVariable Long id) {
        aiService.deleteTravelMate(id);
        return R.success();
    }

    @Operation(summary = "与旅游伙伴聊天", description = "与旅游伙伴进行聊天")
    @PostMapping("/mate/{id}/chat")
    public R<?> chatWithTravelMate(@PathVariable Long id, @RequestParam String message) {
        return R.success(aiService.chatWithTravelMate(id, message));
    }
}
