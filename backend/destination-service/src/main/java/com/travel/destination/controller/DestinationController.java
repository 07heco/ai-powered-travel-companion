package com.travel.destination.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.destination.service.DestinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/destination")
@Tag(name = "目的地服务", description = "提供目的地和景点相关接口")
public class DestinationController {

    @Resource
    private DestinationService destinationService;

    @Operation(summary = "获取目的地列表", description = "分页获取目的地列表")
    @GetMapping("/list")
    public R<?> getDestinations(PageQuery query) {
        return R.success(destinationService.getDestinations(query));
    }

    @Operation(summary = "获取目的地详情", description = "根据ID获取目的地详情")
    @GetMapping("/{id}")
    public R<?> getDestinationById(@PathVariable Long id) {
        return R.success(destinationService.getDestinationById(id));
    }

    @Operation(summary = "获取目的地景点", description = "根据目的地ID获取景点列表")
    @GetMapping("/{id}/attractions")
    public R<?> getAttractionsByDestinationId(@PathVariable Long id) {
        return R.success(destinationService.getAttractionsByDestinationId(id));
    }

    @Operation(summary = "获取热门目的地", description = "获取热门目的地列表")
    @GetMapping("/popular")
    public R<?> getPopularDestinations(@RequestParam(defaultValue = "10") Integer limit) {
        return R.success(destinationService.getPopularDestinations(limit));
    }

    @Operation(summary = "搜索目的地", description = "根据关键词搜索目的地")
    @GetMapping("/search")
    public R<?> searchDestinations(@RequestParam String keyword) {
        return R.success(destinationService.searchDestinations(keyword));
    }
}
