package com.travel.deals.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.deals.entity.Deal;
import com.travel.deals.service.DealsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/deals")
@Tag(name = "优惠服务", description = "提供优惠活动相关接口")
public class DealsController {

    @Resource
    private DealsService dealsService;

    @Operation(summary = "创建优惠活动", description = "创建新的优惠活动")
    @PostMapping
    public R<?> createDeal(@RequestBody Deal deal) {
        return R.success(dealsService.createDeal(deal));
    }

    @Operation(summary = "更新优惠活动", description = "更新优惠活动信息")
    @PutMapping
    public R<?> updateDeal(@RequestBody Deal deal) {
        return R.success(dealsService.updateDeal(deal));
    }

    @Operation(summary = "获取优惠活动详情", description = "根据ID获取优惠活动详情")
    @GetMapping("/{id}")
    public R<?> getDealById(@PathVariable Long id) {
        return R.success(dealsService.getDealById(id));
    }

    @Operation(summary = "删除优惠活动", description = "根据ID删除优惠活动")
    @DeleteMapping("/{id}")
    public R<?> deleteDeal(@PathVariable Long id) {
        dealsService.deleteDeal(id);
        return R.success();
    }

    @Operation(summary = "获取优惠活动列表", description = "分页获取优惠活动列表")
    @GetMapping("/list")
    public R<?> getDeals(PageQuery query) {
        return R.success(dealsService.getDeals(query));
    }

    @Operation(summary = "获取活跃优惠活动", description = "获取当前活跃的优惠活动")
    @GetMapping("/active")
    public R<?> getActiveDeals() {
        return R.success(dealsService.getActiveDeals());
    }

    @Operation(summary = "按类型获取优惠活动", description = "根据类型获取优惠活动")
    @GetMapping("/type/{type}")
    public R<?> getDealsByType(@PathVariable String type) {
        return R.success(dealsService.getDealsByType(type));
    }

    @Operation(summary = "过期优惠活动", description = "过期已失效的优惠活动")
    @PostMapping("/expire")
    public R<?> expireDeals() {
        dealsService.expireDeals();
        return R.success();
    }
}
