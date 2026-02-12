package com.travel.trip.service.impl;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;
import com.travel.trip.entity.TripCollaborator;
import com.travel.trip.entity.TripComment;
import com.travel.trip.entity.TripAttachment;
import com.travel.trip.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TripServiceImplTest {

    @Autowired
    private TripService tripService;

    private Trip testTrip;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testTrip = new Trip();
        testTrip.setUserId(1L);
        testTrip.setTripName("测试行程");
        testTrip.setDestination("北京");
        testTrip.setStartDate(LocalDateTime.now());
        testTrip.setEndDate(LocalDateTime.now().plusDays(5));
        testTrip.setStatus("PLANNING");
        testTrip.setDescription("测试行程描述");
    }

    @Test
    void testGetUserTrips() {
        PageQuery query = new PageQuery();
        query.setPage(1);
        query.setSize(10);
        
        PageResult<Trip> result = tripService.getUserTrips(1L, query);
        assertNotNull(result);
        assertNotNull(result.getRecords());
        assertTrue(result.getTotal() >= 0);
    }

    @Test
    void testGetTripById() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 测试获取行程
        Trip trip = tripService.getTripById(created.getId());
        assertNotNull(trip);
        assertEquals("测试行程", trip.getTripName());
    }

    @Test
    void testCreateTrip() {
        Trip created = tripService.createTrip(testTrip);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("测试行程", created.getTripName());
    }

    @Test
    void testUpdateTrip() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 更新行程信息
        created.setTripName("更新后的行程名称");
        created.setDescription("更新后的行程描述");
        
        Trip updated = tripService.updateTrip(created);
        assertNotNull(updated);
        assertEquals("更新后的行程名称", updated.getTripName());
        assertEquals("更新后的行程描述", updated.getDescription());
    }

    @Test
    void testDeleteTrip() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 删除行程
        tripService.deleteTrip(created.getId());
        
        // 验证行程已删除
        Trip trip = tripService.getTripById(created.getId());
        assertNull(trip);
    }

    @Test
    void testGetTripDetails() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 创建行程详情
        TripDetail detail = new TripDetail();
        detail.setTripId(created.getId());
        detail.setDay(1);
        detail.setTitle("第一天");
        detail.setDescription("第一天的行程");
        tripService.createTripDetail(detail);
        
        // 获取行程详情
        List<TripDetail> details = tripService.getTripDetails(created.getId());
        assertNotNull(details);
        assertTrue(details.size() > 0);
    }

    @Test
    void testCreateTripDetail() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 创建行程详情
        TripDetail detail = new TripDetail();
        detail.setTripId(created.getId());
        detail.setDay(1);
        detail.setTitle("第一天");
        detail.setDescription("第一天的行程");
        
        TripDetail createdDetail = tripService.createTripDetail(detail);
        assertNotNull(createdDetail);
        assertNotNull(createdDetail.getId());
        assertEquals(1, createdDetail.getDay());
    }

    @Test
    void testAddCollaborator() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 添加协作成员
        TripCollaborator collaborator = new TripCollaborator();
        collaborator.setTripId(created.getId());
        collaborator.setUserId(2L);
        collaborator.setRole("EDITOR");
        collaborator.setStatus("PENDING");
        
        TripCollaborator createdCollaborator = tripService.addCollaborator(collaborator);
        assertNotNull(createdCollaborator);
        assertNotNull(createdCollaborator.getId());
        assertEquals(2L, createdCollaborator.getUserId());
    }

    @Test
    void testAddComment() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 添加评论
        TripComment comment = new TripComment();
        comment.setTripId(created.getId());
        comment.setUserId(2L);
        comment.setContent("这是一条测试评论");
        
        TripComment createdComment = tripService.addComment(comment);
        assertNotNull(createdComment);
        assertNotNull(createdComment.getId());
        assertEquals("这是一条测试评论", createdComment.getContent());
    }

    @Test
    void testAddAttachment() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 添加附件
        TripAttachment attachment = new TripAttachment();
        attachment.setTripId(created.getId());
        attachment.setUserId(1L);
        attachment.setFileName("测试文件.txt");
        attachment.setFilePath("/uploads/test.txt");
        attachment.setFileSize(1024L);
        attachment.setFileType("text/plain");
        
        TripAttachment createdAttachment = tripService.addAttachment(attachment);
        assertNotNull(createdAttachment);
        assertNotNull(createdAttachment.getId());
        assertEquals("测试文件.txt", createdAttachment.getFileName());
    }

    @Test
    void testGetTripStatistics() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 获取统计信息
        Map<String, Object> statistics = tripService.getTripStatistics(created.getId());
        assertNotNull(statistics);
        assertEquals(created.getId(), statistics.get("tripId"));
        assertEquals("测试行程", statistics.get("tripName"));
    }

    @Test
    void testShareTrip() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        
        // 分享行程
        Trip shared = tripService.shareTrip(created.getId(), true);
        assertNotNull(shared);
        assertEquals(1, shared.getIsPublic());
        assertEquals(1, shared.getIsShared());
    }

    @Test
    void testGetPublicTrips() {
        PageQuery query = new PageQuery();
        query.setPage(1);
        query.setSize(10);
        
        List<Trip> trips = tripService.getPublicTrips(query);
        assertNotNull(trips);
    }

    @Test
    void testIncrementViewCount() {
        // 先创建一个行程
        Trip created = tripService.createTrip(testTrip);
        int initialViewCount = created.getViewCount();
        
        // 增加浏览次数
        tripService.incrementViewCount(created.getId());
        
        // 验证浏览次数已增加
        Trip updated = tripService.getTripById(created.getId());
        assertTrue(updated.getViewCount() > initialViewCount);
    }
}
