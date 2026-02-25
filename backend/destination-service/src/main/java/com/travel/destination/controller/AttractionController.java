package com.travel.destination.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.destination.entity.Attraction;
import com.travel.destination.entity.AttractionImage;
import com.travel.destination.entity.AttractionReview;
import com.travel.destination.entity.AttractionTag;
import com.travel.destination.service.AttractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attraction")
@Tag(name = "景点服务", description = "提供景点管理相关接口")
public class AttractionController {

    @Resource
    private AttractionService attractionService;

    @Operation(summary = "获取景点列表", description = "分页获取景点列表，支持按目的地、关键词、分类筛选")
    @GetMapping("/list")
    public R<?> getAttractions(PageQuery query) {
        return R.success(attractionService.getAttractions(query));
    }

    @Operation(summary = "获取景点详情", description = "根据ID获取景点详情")
    @GetMapping("/{id}")
    public R<?> getAttractionById(@PathVariable Long id) {
        return R.success(attractionService.getAttractionById(id));
    }

    @Operation(summary = "创建景点", description = "创建新的景点")
    @PostMapping
    public R<?> createAttraction(@RequestBody Attraction attraction) {
        return R.success(attractionService.createAttraction(attraction));
    }

    @Operation(summary = "更新景点", description = "根据ID更新景点信息")
    @PutMapping("/{id}")
    public R<?> updateAttraction(@PathVariable Long id, @RequestBody Attraction attraction) {
        return R.success(attractionService.updateAttraction(id, attraction));
    }

    @Operation(summary = "删除景点", description = "根据ID删除景点（逻辑删除）")
    @DeleteMapping("/{id}")
    public R<?> deleteAttraction(@PathVariable Long id) {
        return R.success(attractionService.deleteAttraction(id));
    }

    @Operation(summary = "获取景点评价", description = "根据景点ID获取评价列表")
    @GetMapping("/{id}/reviews")
    public R<?> getReviewsByAttractionId(@PathVariable Long id) {
        return R.success(attractionService.getReviewsByAttractionId(id));
    }

    @Operation(summary = "获取热门景点", description = "获取热门景点列表")
    @GetMapping("/popular")
    public R<?> getPopularAttractions(@RequestParam(defaultValue = "10") Integer limit) {
        return R.success(attractionService.getPopularAttractions(limit));
    }

    @Operation(summary = "搜索景点", description = "根据关键词搜索景点")
    @GetMapping("/search")
    public R<?> searchAttractions(@RequestParam String keyword) {
        return R.success(attractionService.searchAttractions(keyword));
    }

    @Operation(summary = "按分类获取景点", description = "根据分类获取景点列表")
    @GetMapping("/category/{category}")
    public R<?> getAttractionsByCategory(@PathVariable String category, @RequestParam(defaultValue = "20") Integer limit) {
        return R.success(attractionService.getAttractionsByCategory(category, limit));
    }

    @Operation(summary = "获取景点图片", description = "根据景点ID获取图片列表")
    @GetMapping("/{id}/images")
    public R<?> getAttractionImages(@PathVariable Long id) {
        return R.success(attractionService.getAttractionImages(id));
    }

    @Operation(summary = "添加景点图片", description = "为景点添加图片")
    @PostMapping("/{id}/images")
    public R<?> addAttractionImage(@PathVariable Long id, @RequestBody AttractionImage image) {
        return R.success(attractionService.addAttractionImage(id, image));
    }

    @Operation(summary = "删除景点图片", description = "根据图片ID删除景点图片")
    @DeleteMapping("/images/{id}")
    public R<?> deleteAttractionImage(@PathVariable Long id) {
        return R.success(attractionService.deleteAttractionImage(id));
    }

    @Operation(summary = "获取景点标签", description = "根据景点ID获取标签列表")
    @GetMapping("/{id}/tags")
    public R<?> getAttractionTags(@PathVariable Long id) {
        return R.success(attractionService.getAttractionTags(id));
    }

    @Operation(summary = "添加景点标签", description = "为景点添加标签")
    @PostMapping("/{id}/tags")
    public R<?> addAttractionTag(@PathVariable Long id, @RequestBody AttractionTag tag) {
        return R.success(attractionService.addAttractionTag(id, tag));
    }

    @Operation(summary = "删除景点标签", description = "根据标签ID删除景点标签")
    @DeleteMapping("/tags/{id}")
    public R<?> deleteAttractionTag(@PathVariable Long id) {
        return R.success(attractionService.deleteAttractionTag(id));
    }

    @Operation(summary = "添加景点评价", description = "为景点添加评价")
    @PostMapping("/reviews")
    public R<?> addAttractionReview(@RequestBody AttractionReview review) {
        return R.success(attractionService.addAttractionReview(review));
    }

    @Operation(summary = "更新景点评价", description = "根据评价ID更新评价")
    @PutMapping("/reviews/{id}")
    public R<?> updateAttractionReview(@PathVariable Long id, @RequestBody AttractionReview review) {
        return R.success(attractionService.updateAttractionReview(id, review));
    }

    @Operation(summary = "删除景点评价", description = "根据评价ID删除景点评价")
    @DeleteMapping("/reviews/{id}")
    public R<?> deleteAttractionReview(@PathVariable Long id) {
        return R.success(attractionService.deleteAttractionReview(id));
    }

    @Operation(summary = "按标签获取景点", description = "根据标签名称获取景点列表")
    @GetMapping("/tag/{tagName}")
    public R<?> getAttractionsByTag(@PathVariable String tagName, @RequestParam(defaultValue = "20") Integer limit) {
        return R.success(attractionService.getAttractionsByTag(tagName, limit));
    }

    @Operation(summary = "获取景点统计信息", description = "获取景点相关统计信息")
    @GetMapping("/statistics")
    public R<?> getAttractionStatistics() {
        return R.success(attractionService.getAttractionStatistics());
    }

    @Operation(summary = "获取推荐景点", description = "根据用户ID获取推荐景点")
    @GetMapping("/recommend")
    public R<?> recommendAttractions(@RequestParam Long userId, @RequestParam(defaultValue = "10") Integer limit) {
        return R.success(attractionService.recommendAttractions(userId, limit));
    }
}