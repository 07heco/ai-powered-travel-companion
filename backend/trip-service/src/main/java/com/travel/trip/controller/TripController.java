package com.travel.trip.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;
import com.travel.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/trip")
@Tag(name = "行程服务", description = "提供行程管理相关接口")
public class TripController {

    @Resource
    private TripService tripService;

    @Operation(summary = "获取用户行程列表", description = "分页获取用户行程列表")
    @GetMapping("/user/{userId}")
    public R<?> getUserTrips(@PathVariable Long userId, PageQuery query) {
        return R.success(tripService.getUserTrips(userId, query));
    }

    @Operation(summary = "获取行程详情", description = "根据ID获取行程详情")
    @GetMapping("/{id}")
    public R<?> getTripById(@PathVariable Long id) {
        return R.success(tripService.getTripById(id));
    }

    @Operation(summary = "创建行程", description = "创建新的行程")
    @PostMapping
    public R<?> createTrip(@RequestBody Trip trip) {
        return R.success(tripService.createTrip(trip));
    }

    @Operation(summary = "更新行程", description = "更新行程信息")
    @PutMapping
    public R<?> updateTrip(@RequestBody Trip trip) {
        return R.success(tripService.updateTrip(trip));
    }

    @Operation(summary = "删除行程", description = "根据ID删除行程")
    @DeleteMapping("/{id}")
    public R<?> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return R.success();
    }

    @Operation(summary = "获取行程详情列表", description = "根据行程ID获取行程详情列表")
    @GetMapping("/{id}/details")
    public R<?> getTripDetails(@PathVariable Long id) {
        return R.success(tripService.getTripDetails(id));
    }

    @Operation(summary = "获取行程详情", description = "根据ID获取行程详情")
    @GetMapping("/detail/{id}")
    public R<?> getTripDetailById(@PathVariable Long id) {
        return R.success(tripService.getTripDetailById(id));
    }

    @Operation(summary = "创建行程详情", description = "创建新的行程详情")
    @PostMapping("/detail")
    public R<?> createTripDetail(@RequestBody TripDetail tripDetail) {
        return R.success(tripService.createTripDetail(tripDetail));
    }

    @Operation(summary = "更新行程详情", description = "更新行程详情信息")
    @PutMapping("/detail")
    public R<?> updateTripDetail(@RequestBody TripDetail tripDetail) {
        return R.success(tripService.updateTripDetail(tripDetail));
    }

    @Operation(summary = "删除行程详情", description = "根据ID删除行程详情")
    @DeleteMapping("/detail/{id}")
    public R<?> deleteTripDetail(@PathVariable Long id) {
        tripService.deleteTripDetail(id);
        return R.success();
    }
}
