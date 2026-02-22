package com.travel.ai.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.ai.entity.RecruitPost;
import com.travel.ai.entity.TravelMateMatch;
import com.travel.ai.service.TravelMateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@Tag(name = "旅伴服务", description = "提供旅伴匹配和招募相关接口，包括AI匹配、招募帖管理和匹配分析功能")
public class TravelMateController {

    @Resource
    private TravelMateService travelMateService;

    @Operation(
            summary = "匹配旅伴", 
            description = "根据用户的目的地、旅行日期、旅行风格和兴趣爱好，智能匹配最合适的旅伴"
    )
    @PostMapping("/travelmate/match")
    public R<?> matchTravelMates(
            @RequestBody TravelMateMatchRequest request
    ) {
        return R.success(travelMateService.matchTravelMates(request.getUserId(), request.getDestination(), request.getTravelDate(), request.getTravelStyle(), request.getInterests()));
    }
    
    /**
     * 旅伴匹配请求DTO
     */
    public static class TravelMateMatchRequest {
        private Long userId;
        private String destination;
        private String travelDate;
        private String travelStyle;
        private String interests;
        
        // Getter and Setter methods
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }
        public String getTravelDate() { return travelDate; }
        public void setTravelDate(String travelDate) { this.travelDate = travelDate; }
        public String getTravelStyle() { return travelStyle; }
        public void setTravelStyle(String travelStyle) { this.travelStyle = travelStyle; }
        public String getInterests() { return interests; }
        public void setInterests(String interests) { this.interests = interests; }
    }

    @Operation(
            summary = "获取用户匹配列表", 
            description = "分页获取用户的旅伴匹配列表，按匹配分数排序"
    )
    @GetMapping("/travelmate/matches/{userId}")
    public R<?> getUserMatches(
            @PathVariable Long userId, 
            PageQuery query
    ) {
        return R.success(travelMateService.getUserMatches(userId, query));
    }

    @Operation(
            summary = "创建招募帖", 
            description = "创建新的旅伴招募帖，设置目的地、旅行日期、人数限制等信息"
    )
    @PostMapping("/travelmate/recruit/create")
    public R<?> createRecruitPost(@RequestBody RecruitPost recruitPost) {
        return R.success(travelMateService.createRecruitPost(recruitPost));
    }

    @Operation(
            summary = "获取招募帖列表", 
            description = "分页获取所有活跃的招募帖列表，按创建时间排序"
    )
    @GetMapping("/travelmate/recruit/list")
    public R<?> getRecruitPosts(PageQuery query) {
        return R.success(travelMateService.getRecruitPosts(query));
    }

    @Operation(
            summary = "获取招募帖详情", 
            description = "根据ID获取招募帖的详细信息，包括目的地、旅行日期、人数限制、详细描述等"
    )
    @GetMapping("/travelmate/recruit/{id}")
    public R<?> getRecruitPostById(@PathVariable Long id) {
        return R.success(travelMateService.getRecruitPostById(id));
    }

    @Operation(
            summary = "更新招募帖", 
            description = "更新招募帖的信息，如人数、描述、状态等"
    )
    @PutMapping("/travelmate/recruit/update")
    public R<?> updateRecruitPost(@RequestBody RecruitPost recruitPost) {
        return R.success(travelMateService.updateRecruitPost(recruitPost));
    }

    @Operation(
            summary = "删除招募帖", 
            description = "根据ID删除指定的招募帖"
    )
    @DeleteMapping("/travelmate/recruit/{id}")
    public R<?> deleteRecruitPost(@PathVariable Long id) {
        travelMateService.deleteRecruitPost(id);
        return R.success();
    }

    @Operation(
            summary = "搜索招募帖", 
            description = "根据关键词搜索招募帖，支持按目的地、标签等字段搜索"
    )
    @GetMapping("/travelmate/recruit/search")
    public R<?> searchRecruitPosts(
            @RequestParam(required = false) String keyword, 
            PageQuery query
    ) {
        return R.success(travelMateService.searchRecruitPosts(keyword, query));
    }

    @Operation(
            summary = "获取匹配分析", 
            description = "获取用户的旅伴匹配分析，包括平均匹配分数、目的地分布、旅行风格分布等"
    )
    @GetMapping("/travelmate/analysis/{userId}")
    public R<?> getMatchAnalysis(@PathVariable Long userId) {
        return R.success(travelMateService.getMatchAnalysis(userId));
    }
}