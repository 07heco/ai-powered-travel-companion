package com.travel.trip.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;
import com.travel.trip.entity.TripCollaborator;
import com.travel.trip.entity.TripComment;
import com.travel.trip.entity.TripAttachment;
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

    // 行程基本操作
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

    // 行程详情操作
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

    // 行程协作操作
    @Operation(summary = "获取行程协作列表", description = "根据行程ID获取协作列表")
    @GetMapping("/{id}/collaborators")
    public R<?> getTripCollaborators(@PathVariable Long id) {
        return R.success(tripService.getTripCollaborators(id));
    }

    @Operation(summary = "添加行程协作", description = "添加行程协作成员")
    @PostMapping("/collaborator")
    public R<?> addCollaborator(@RequestBody TripCollaborator collaborator) {
        return R.success(tripService.addCollaborator(collaborator));
    }

    @Operation(summary = "更新行程协作", description = "更新行程协作信息")
    @PutMapping("/collaborator/{id}")
    public R<?> updateCollaborator(@PathVariable Long id, @RequestBody TripCollaborator collaborator) {
        return R.success(tripService.updateCollaborator(id, collaborator));
    }

    @Operation(summary = "删除行程协作", description = "根据ID删除行程协作")
    @DeleteMapping("/collaborator/{id}")
    public R<?> removeCollaborator(@PathVariable Long id) {
        tripService.removeCollaborator(id);
        return R.success();
    }

    @Operation(summary = "获取用户协作行程", description = "获取用户参与协作的行程列表")
    @GetMapping("/collaborator/user/{userId}")
    public R<?> getCollaboratorTrips(@PathVariable Long userId) {
        return R.success(tripService.getCollaboratorTrips(userId));
    }

    // 行程评论操作
    @Operation(summary = "获取行程评论列表", description = "根据行程ID获取评论列表")
    @GetMapping("/{id}/comments")
    public R<?> getTripComments(@PathVariable Long id) {
        return R.success(tripService.getTripComments(id));
    }

    @Operation(summary = "添加行程评论", description = "添加行程评论")
    @PostMapping("/comment")
    public R<?> addComment(@RequestBody TripComment comment) {
        return R.success(tripService.addComment(comment));
    }

    @Operation(summary = "更新行程评论", description = "更新行程评论")
    @PutMapping("/comment/{id}")
    public R<?> updateComment(@PathVariable Long id, @RequestBody TripComment comment) {
        return R.success(tripService.updateComment(id, comment));
    }

    @Operation(summary = "删除行程评论", description = "根据ID删除行程评论")
    @DeleteMapping("/comment/{id}")
    public R<?> deleteComment(@PathVariable Long id) {
        tripService.deleteComment(id);
        return R.success();
    }

    // 行程附件操作
    @Operation(summary = "获取行程附件列表", description = "根据行程ID获取附件列表")
    @GetMapping("/{id}/attachments")
    public R<?> getTripAttachments(@PathVariable Long id) {
        return R.success(tripService.getTripAttachments(id));
    }

    @Operation(summary = "获取行程详情附件", description = "根据行程详情ID获取附件列表")
    @GetMapping("/detail/{id}/attachments")
    public R<?> getTripDetailAttachments(@PathVariable Long id) {
        return R.success(tripService.getTripDetailAttachments(id));
    }

    @Operation(summary = "添加行程附件", description = "添加行程附件")
    @PostMapping("/attachment")
    public R<?> addAttachment(@RequestBody TripAttachment attachment) {
        return R.success(tripService.addAttachment(attachment));
    }

    @Operation(summary = "删除行程附件", description = "根据ID删除行程附件")
    @DeleteMapping("/attachment/{id}")
    public R<?> deleteAttachment(@PathVariable Long id) {
        tripService.deleteAttachment(id);
        return R.success();
    }

    // 行程统计和分享
    @Operation(summary = "获取行程统计信息", description = "获取行程统计信息")
    @GetMapping("/{id}/statistics")
    public R<?> getTripStatistics(@PathVariable Long id) {
        return R.success(tripService.getTripStatistics(id));
    }

    @Operation(summary = "分享行程", description = "分享行程")
    @PostMapping("/{id}/share")
    public R<?> shareTrip(@PathVariable Long id, @RequestParam boolean isPublic) {
        return R.success(tripService.shareTrip(id, isPublic));
    }

    @Operation(summary = "获取公开行程列表", description = "获取公开行程列表")
    @GetMapping("/public")
    public R<?> getPublicTrips(PageQuery query) {
        return R.success(tripService.getPublicTrips(query));
    }

    @Operation(summary = "增加行程浏览次数", description = "增加行程浏览次数")
    @PostMapping("/{id}/view")
    public R<?> incrementViewCount(@PathVariable Long id) {
        tripService.incrementViewCount(id);
        return R.success();
    }
}
