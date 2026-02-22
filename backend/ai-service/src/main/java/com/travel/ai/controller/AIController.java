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
@RequestMapping("")
@Tag(name = "AI服务", description = "提供智能旅游助手相关接口，包括AI旅行计划生成和旅游伙伴聊天功能")
public class AIController {

    @Operation(
            summary = "欢迎页面", 
            description = "AI服务欢迎页面，提供服务信息和接口导航"
    )
    @GetMapping("/")
    public R<?> welcome() {
        return R.success("AI服务欢迎您！请访问 /swagger-ui.html 查看API文档");
    }

    @Resource
    private AIService aiService;

    @Operation(
            summary = "生成AI旅行计划", 
            description = "根据目的地、日期和偏好生成详细的AI旅行计划，包含每日行程、推荐景点、餐饮建议等"
    )
    @PostMapping("/plan/generate")
    public R<?> generateAIPlan(
            @RequestBody PlanRequest request
    ) {
        return R.success(aiService.generateAIPlan(request.getUserId(), request.getDestination(), request.getStartDate(), request.getEndDate(), request.getPreferences()));
    }
    
    /**
     * 旅行计划请求DTO
     */
    public static class PlanRequest {
        private Long userId;
        private String destination;
        private String startDate;
        private String endDate;
        private String preferences;
        
        // Getter and Setter methods
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }
        public String getStartDate() { return startDate; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
        public String getPreferences() { return preferences; }
        public void setPreferences(String preferences) { this.preferences = preferences; }
    }

    @Operation(
            summary = "获取AI计划详情", 
            description = "根据ID获取AI旅行计划的详细信息，包括完整的行程安排和建议"
    )
    @GetMapping("/plan/{id}")
    public R<?> getAIPlanById(@PathVariable Long id) {
        return R.success(aiService.getAIPlanById(id));
    }

    @Operation(
            summary = "获取用户AI计划列表", 
            description = "分页获取用户的AI旅行计划列表，支持按创建时间排序"
    )
    @GetMapping("/plan/user/{userId}")
    public R<?> getUserAIPlans(
            @PathVariable Long userId, 
            PageQuery query
    ) {
        return R.success(aiService.getUserAIPlans(userId, query));
    }

    @Operation(
            summary = "删除AI计划", 
            description = "根据ID删除指定的AI旅行计划"
    )
    @DeleteMapping("/plan/{id}")
    public R<?> deleteAIPlan(@PathVariable Long id) {
        aiService.deleteAIPlan(id);
        return R.success();
    }

    @Operation(
            summary = "创建旅游伙伴", 
            description = "创建新的旅游伙伴，设置名称、个性和专业领域"
    )
    @PostMapping("/mate/create")
    public R<?> createTravelMate(
            @RequestBody TravelMateRequest request
    ) {
        return R.success(aiService.createTravelMate(request.getUserId(), request.getName(), request.getPersonality(), request.getExpertise()));
    }
    
    /**
     * 旅游伙伴请求DTO
     */
    public static class TravelMateRequest {
        private Long userId;
        private String name;
        private String personality;
        private String expertise;
        
        // Getter and Setter methods
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPersonality() { return personality; }
        public void setPersonality(String personality) { this.personality = personality; }
        public String getExpertise() { return expertise; }
        public void setExpertise(String expertise) { this.expertise = expertise; }
    }

    @Operation(
            summary = "获取旅游伙伴详情", 
            description = "根据ID获取旅游伙伴的详细信息，包括个性和专业领域"
    )
    @GetMapping("/mate/{id}")
    public R<?> getTravelMateById(@PathVariable Long id) {
        return R.success(aiService.getTravelMateById(id));
    }

    @Operation(
            summary = "获取用户旅游伙伴列表", 
            description = "获取用户创建的所有旅游伙伴列表"
    )
    @GetMapping("/mate/user/{userId}")
    public R<?> getUserTravelMates(@PathVariable Long userId) {
        return R.success(aiService.getUserTravelMates(userId));
    }

    @Operation(
            summary = "删除旅游伙伴", 
            description = "根据ID删除指定的旅游伙伴"
    )
    @DeleteMapping("/mate/{id}")
    public R<?> deleteTravelMate(@PathVariable Long id) {
        aiService.deleteTravelMate(id);
        return R.success();
    }

    @Operation(
            summary = "与旅游伙伴聊天", 
            description = "与指定的旅游伙伴进行聊天，获取智能旅游建议和信息"
    )
    @PostMapping("/mate/{id}/chat")
    public R<?> chatWithTravelMate(
            @PathVariable Long id, 
            @RequestBody ChatRequest request
    ) {
        return R.success(aiService.chatWithTravelMate(id, request.getMessage()));
    }
    
    /**
     * 聊天请求DTO
     */
    public static class ChatRequest {
        private String message;
        
        // Getter and Setter methods
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
