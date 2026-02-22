package com.travel.ai.controller;

import com.travel.common.vo.R;
import com.travel.ai.entity.ARLandmark;
import com.travel.ai.service.ARService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("")
@Tag(name = "AR服务", description = "提供AR实景导览相关接口，包括景点识别、详情查询和附近景点获取功能")
public class ARController {

    @Resource
    private ARService arService;

    @Operation(
            summary = "识别景点", 
            description = "根据图片URL识别景点，返回景点名称、分类、详细描述等信息"
    )
    @PostMapping("/ar/recognize")
    public R<?> recognizeLandmark(@RequestParam String imageUrl) {
        return R.success(arService.recognizeLandmark(imageUrl));
    }

    @Operation(
            summary = "获取景点详情", 
            description = "根据ID获取景点的详细信息，包括名称、英文名称、分类、评分、描述、历史、开放时间等"
    )
    @GetMapping("/ar/landmark/{id}")
    public R<?> getLandmarkById(@PathVariable Long id) {
        return R.success(arService.getLandmarkById(id));
    }

    @Operation(
            summary = "获取附近景点", 
            description = "根据位置坐标和半径获取附近的景点列表，按距离排序"
    )
    @GetMapping("/ar/nearby")
    public R<?> getNearbyLandmarks(
            @RequestParam Double latitude, 
            @RequestParam Double longitude, 
            @RequestParam Integer radius
    ) {
        return R.success(arService.getNearbyLandmarks(latitude, longitude, radius));
    }

    @Operation(
            summary = "搜索景点", 
            description = "根据关键词搜索景点，支持按名称、描述等字段搜索"
    )
    @GetMapping("/ar/search")
    public R<?> searchLandmarks(@RequestParam String keyword) {
        return R.success(arService.searchLandmarks(keyword));
    }
}