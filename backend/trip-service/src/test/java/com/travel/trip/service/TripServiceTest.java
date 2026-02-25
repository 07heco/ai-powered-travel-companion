package com.travel.trip.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.trip.dao.TripDao;
import com.travel.trip.dao.TripDetailDao;
import com.travel.trip.dao.TripCommentDao;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;
import com.travel.trip.entity.TripComment;
import com.travel.trip.service.impl.TripServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TripServiceTest {

    @Mock
    private TripDao tripDao;

    @Mock
    private TripDetailDao tripDetailDao;

    @Mock
    private TripCommentDao tripCommentDao;

    @InjectMocks
    private TripServiceImpl tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserTrips() {
        // 准备测试数据
        Long userId = 1L;
        PageQuery query = new PageQuery();
        query.setPage(1);
        query.setSize(10);

        // 模拟返回数据
        List<Trip> trips = new ArrayList<>();
        Trip trip = new Trip();
        trip.setId(1L);
        trip.setTripName("测试行程");
        trip.setUserId(userId);
        trip.setStatus(1);
        trip.setEnabled(1);
        trip.setDeleted(0);
        trips.add(trip);

        // 设置mock行为
        IPage<Trip> page = new Page<>();
        page.setRecords(trips);
        page.setTotal(trips.size());
        when(tripDao.selectPage(any(), any())).thenReturn(page);

        // 执行测试
        PageResult<Trip> result = tripService.getUserTrips(userId, query);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
    }

    @Test
    void getTripById() {
        // 准备测试数据
        Long tripId = 1L;

        // 模拟返回数据
        Trip trip = new Trip();
        trip.setId(tripId);
        trip.setTripName("测试行程");
        trip.setViewCount(0);
        trip.setStatus(1);
        trip.setEnabled(1);
        trip.setDeleted(0);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(trip);
        when(tripDao.updateById(any())).thenReturn(1);

        // 执行测试
        Trip result = tripService.getTripById(tripId);

        // 验证结果
        assertNotNull(result);
        assertEquals(tripId, result.getId());
    }

    @Test
    void createTrip() {
        // 准备测试数据
        Trip trip = new Trip();
        trip.setTripName("测试行程");
        trip.setUserId(1L);
        trip.setStartDate(LocalDate.now());
        trip.setEndDate(LocalDate.now().plusDays(3));

        // 设置mock行为
        when(tripDao.insert(any())).thenReturn(1);

        // 执行测试
        Trip result = tripService.createTrip(trip);

        // 验证结果
        assertNotNull(result);
        assertEquals("测试行程", result.getTripName());
    }

    @Test
    void updateTrip() {
        // 准备测试数据
        Trip trip = new Trip();
        trip.setId(1L);
        trip.setTripName("更新后的行程");
        trip.setUserId(1L);

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(1L);
        existingTrip.setTripName("原行程");
        existingTrip.setDeleted(0);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDao.updateById(any())).thenReturn(1);

        // 执行测试
        Trip result = tripService.updateTrip(trip);

        // 验证结果
        assertNotNull(result);
        assertEquals("更新后的行程", result.getTripName());
    }

    @Test
    void deleteTrip() {
        // 准备测试数据
        Long tripId = 1L;

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(tripId);
        existingTrip.setDeleted(0);

        List<TripDetail> tripDetails = new ArrayList<>();
        List<TripComment> tripComments = new ArrayList<>();

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDao.updateById(any())).thenReturn(1);
        when(tripDetailDao.selectList(any())).thenReturn(tripDetails);
        when(tripDetailDao.updateById(any())).thenReturn(1);
        when(tripCommentDao.selectList(any())).thenReturn(tripComments);
        when(tripCommentDao.updateById(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> tripService.deleteTrip(tripId));
    }

    @Test
    void getTripDetails() {
        // 准备测试数据
        Long tripId = 1L;

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(tripId);
        existingTrip.setDeleted(0);

        List<TripDetail> details = new ArrayList<>();
        TripDetail detail = new TripDetail();
        detail.setId(1L);
        detail.setTripId(tripId);
        detail.setStatus(1);
        detail.setDeleted(0);
        details.add(detail);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDetailDao.selectList(any())).thenReturn(details);

        // 执行测试
        List<TripDetail> result = tripService.getTripDetails(tripId);

        // 验证结果
        assertNotNull(result);
    }

    @Test
    void getTripDetailById() {
        // 准备测试数据
        Long detailId = 1L;

        // 模拟返回数据
        TripDetail detail = new TripDetail();
        detail.setId(detailId);
        detail.setStatus(1);
        detail.setDeleted(0);

        // 设置mock行为
        when(tripDetailDao.selectOne(any())).thenReturn(detail);

        // 执行测试
        TripDetail result = tripService.getTripDetailById(detailId);

        // 验证结果
        assertNotNull(result);
        assertEquals(detailId, result.getId());
    }

    @Test
    void createTripDetail() {
        // 准备测试数据
        TripDetail detail = new TripDetail();
        detail.setTripId(1L);
        detail.setTitle("测试详情");
        detail.setDay(1);

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(1L);
        existingTrip.setUserId(1L);
        existingTrip.setDeleted(0);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDetailDao.insert(any())).thenReturn(1);

        // 执行测试
        TripDetail result = tripService.createTripDetail(detail);

        // 验证结果
        assertNotNull(result);
        assertEquals("测试详情", result.getTitle());
    }

    @Test
    void updateTripDetail() {
        // 准备测试数据
        TripDetail detail = new TripDetail();
        detail.setId(1L);
        detail.setTitle("更新后的详情");

        // 模拟返回数据
        TripDetail existingDetail = new TripDetail();
        existingDetail.setId(1L);
        existingDetail.setTitle("原详情");
        existingDetail.setCreatedBy(1L);
        existingDetail.setDeleted(0);

        // 设置mock行为
        when(tripDetailDao.selectOne(any())).thenReturn(existingDetail);
        when(tripDetailDao.updateById(any())).thenReturn(1);

        // 执行测试
        TripDetail result = tripService.updateTripDetail(detail);

        // 验证结果
        assertNotNull(result);
        assertEquals("更新后的详情", result.getTitle());
    }

    @Test
    void deleteTripDetail() {
        // 准备测试数据
        Long detailId = 1L;

        // 模拟返回数据
        TripDetail existingDetail = new TripDetail();
        existingDetail.setId(detailId);
        existingDetail.setDeleted(0);

        // 设置mock行为
        when(tripDetailDao.selectOne(any())).thenReturn(existingDetail);
        when(tripDetailDao.updateById(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> tripService.deleteTripDetail(detailId));
    }

    @Test
    void getTripComments() {
        // 准备测试数据
        Long tripId = 1L;

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(tripId);
        existingTrip.setDeleted(0);

        List<TripComment> comments = new ArrayList<>();
        TripComment comment = new TripComment();
        comment.setId(1L);
        comment.setTripId(tripId);
        comment.setStatus(1);
        comment.setDeleted(0);
        comments.add(comment);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripCommentDao.selectList(any())).thenReturn(comments);

        // 执行测试
        List<TripComment> result = tripService.getTripComments(tripId);

        // 验证结果
        assertNotNull(result);
    }

    @Test
    void addComment() {
        // 准备测试数据
        TripComment comment = new TripComment();
        comment.setTripId(1L);
        comment.setUserId(1L);
        comment.setContent("测试评论");

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(1L);
        existingTrip.setCommentCount(0);
        existingTrip.setDeleted(0);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripCommentDao.insert(any())).thenReturn(1);
        when(tripDao.updateById(any())).thenReturn(1);

        // 执行测试
        TripComment result = tripService.addComment(comment);

        // 验证结果
        assertNotNull(result);
        assertEquals("测试评论", result.getContent());
    }

    @Test
    void updateComment() {
        // 准备测试数据
        Long commentId = 1L;
        TripComment comment = new TripComment();
        comment.setContent("更新后的评论");
        comment.setUserId(1L);

        // 模拟返回数据
        TripComment existingComment = new TripComment();
        existingComment.setId(commentId);
        existingComment.setContent("原评论");
        existingComment.setDeleted(0);

        // 设置mock行为
        when(tripCommentDao.selectOne(any())).thenReturn(existingComment);
        when(tripCommentDao.updateById(any())).thenReturn(1);

        // 执行测试
        TripComment result = tripService.updateComment(commentId, comment);

        // 验证结果
        assertNotNull(result);
        assertEquals("更新后的评论", result.getContent());
    }

    @Test
    void deleteComment() {
        // 准备测试数据
        Long commentId = 1L;

        // 模拟返回数据
        TripComment existingComment = new TripComment();
        existingComment.setId(commentId);
        existingComment.setTripId(1L);
        existingComment.setDeleted(0);

        Trip existingTrip = new Trip();
        existingTrip.setId(1L);
        existingTrip.setCommentCount(1);
        existingTrip.setDeleted(0);

        // 设置mock行为
        when(tripCommentDao.selectOne(any())).thenReturn(existingComment);
        when(tripCommentDao.updateById(any())).thenReturn(1);
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDao.updateById(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> tripService.deleteComment(commentId));
    }

    @Test
    void getTripStatistics() {
        // 准备测试数据
        Long tripId = 1L;

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(tripId);
        existingTrip.setTripName("测试行程");
        existingTrip.setStartDate(LocalDate.now());
        existingTrip.setEndDate(LocalDate.now().plusDays(3));
        existingTrip.setDeleted(0);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDetailDao.selectCount(any())).thenReturn(0L);
        when(tripCommentDao.selectCount(any())).thenReturn(0L);

        // 执行测试
        Map<String, Object> result = tripService.getTripStatistics(tripId);

        // 验证结果
        assertNotNull(result);
        assertEquals(tripId, result.get("tripId"));
    }

    @Test
    void shareTrip() {
        // 准备测试数据
        Long tripId = 1L;
        boolean isPublic = true;

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(tripId);
        existingTrip.setDeleted(0);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDao.updateById(any())).thenReturn(1);

        // 执行测试
        Trip result = tripService.shareTrip(tripId, isPublic);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getIsShared());
        assertEquals(1, result.getIsPublic());
    }

    @Test
    void getPublicTrips() {
        // 准备测试数据
        PageQuery query = new PageQuery();
        query.setPage(1);
        query.setSize(10);

        // 模拟返回数据
        List<Trip> trips = new ArrayList<>();
        Trip trip = new Trip();
        trip.setId(1L);
        trip.setTripName("测试行程");
        trip.setIsPublic(1);
        trip.setStatus(1);
        trip.setEnabled(1);
        trip.setDeleted(0);
        trips.add(trip);

        // 设置mock行为
        IPage<Trip> page = new Page<>();
        page.setRecords(trips);
        page.setTotal(trips.size());
        when(tripDao.selectPage(any(), any())).thenReturn(page);

        // 执行测试
        List<Trip> result = tripService.getPublicTrips(query);

        // 验证结果
        assertNotNull(result);
    }

    @Test
    void incrementViewCount() {
        // 准备测试数据
        Long tripId = 1L;

        // 模拟返回数据
        Trip existingTrip = new Trip();
        existingTrip.setId(tripId);
        existingTrip.setViewCount(0);
        existingTrip.setDeleted(0);

        // 设置mock行为
        when(tripDao.selectOne(any())).thenReturn(existingTrip);
        when(tripDao.updateById(any())).thenReturn(1);

        // 执行测试
        assertDoesNotThrow(() -> tripService.incrementViewCount(tripId));
    }
}
