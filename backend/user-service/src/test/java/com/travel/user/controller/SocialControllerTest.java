package com.travel.user.controller;

import com.travel.common.vo.R;
import com.travel.user.service.SocialService;
import com.travel.user.entity.UserRelation;
import com.travel.user.entity.TravelCompanionMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SocialController单元测试
 */
public class SocialControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private SocialController socialController;

    @Mock
    private SocialService socialService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(socialController).build();
    }

    /**
     * 测试关注用户
     */
    @Test
    public void testFollowUser() throws Exception {
        // 准备测试数据
        Long targetUserId = 2L;

        // 模拟服务调用
        when(socialService.followUser(1L, targetUserId)).thenReturn(true);

        // 执行测试
        mockMvc.perform(post("/api/social/follow")
                .param("targetUserId", targetUserId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));

        // 验证服务调用
        verify(socialService, times(1)).followUser(1L, targetUserId);
    }

    /**
     * 测试取消关注
     */
    @Test
    public void testUnfollowUser() throws Exception {
        // 准备测试数据
        Long targetUserId = 2L;

        // 模拟服务调用
        when(socialService.unfollowUser(1L, targetUserId)).thenReturn(true);

        // 执行测试
        mockMvc.perform(post("/api/social/unfollow")
                .param("targetUserId", targetUserId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));

        // 验证服务调用
        verify(socialService, times(1)).unfollowUser(1L, targetUserId);
    }

    /**
     * 测试获取关注列表
     */
    @Test
    public void testGetFollowingList() throws Exception {
        // 准备测试数据
        List<UserRelation> expectedList = new ArrayList<>();
        UserRelation relation = new UserRelation();
        relation.setFromUserId(1L);
        relation.setToUserId(2L);
        expectedList.add(relation);

        // 模拟服务调用
        when(socialService.getFollowingList(1L, 1, 20)).thenReturn(expectedList);

        // 执行测试
        mockMvc.perform(get("/api/social/following")
                .param("page", "1")
                .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].userId").value(1))
                .andExpect(jsonPath("$.data[0].targetUserId").value(2));

        // 验证服务调用
        verify(socialService, times(1)).getFollowingList(1L, 1, 20);
    }

    /**
     * 测试推荐旅伴
     */
    @Test
    public void testRecommendTravelCompanions() throws Exception {
        // 准备测试数据
        List<TravelCompanionMatch> expectedList = new ArrayList<>();
        TravelCompanionMatch match = new TravelCompanionMatch();
        match.setId(1L);
        match.setUserId(1L);
        match.setMatchUserId(3L);
        match.setMatchScore(95);
        expectedList.add(match);

        // 模拟服务调用
        when(socialService.recommendTravelCompanions(1L, 1, 20)).thenReturn(expectedList);

        // 执行测试
        mockMvc.perform(get("/api/social/match/recommend")
                .param("page", "1")
                .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].userId").value(1))
                .andExpect(jsonPath("$.data[0].matchUserId").value(3))
                .andExpect(jsonPath("$.data[0].matchScore").value(95));

        // 验证服务调用
        verify(socialService, times(1)).recommendTravelCompanions(1L, 1, 20);
    }
}
