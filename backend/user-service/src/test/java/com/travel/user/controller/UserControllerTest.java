package com.travel.user.controller;

import com.travel.common.vo.R;
import com.travel.user.service.UserService;
import com.travel.user.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserController单元测试
 */
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * 测试获取用户信息
     */
    @Test
    public void testGetUserInfo() throws Exception {
        // 准备测试数据
        UserInfoResponse expectedResponse = new UserInfoResponse();
        expectedResponse.setId(1L);
        expectedResponse.setPhone("13800138000");
        expectedResponse.setNickname("旅行者");
        expectedResponse.setCity("北京");

        // 模拟服务调用
        when(userService.getUserInfo(1L)).thenReturn(expectedResponse);

        // 执行测试
        mockMvc.perform(get("/api/user/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.phone").value("13800138000"))
                .andExpect(jsonPath("$.data.nickname").value("旅行者"))
                .andExpect(jsonPath("$.data.city").value("北京"));

        // 验证服务调用
        verify(userService, times(1)).getUserInfo(1L);
    }

    /**
     * 测试更新用户信息
     */
    @Test
    public void testUpdateUserInfo() throws Exception {
        // 准备测试数据
        UpdateUserInfoRequest updateRequest = new UpdateUserInfoRequest();
        updateRequest.setNickname("旅行者");
        updateRequest.setCity("上海");

        // 模拟服务调用
        when(userService.updateUserInfo(1L, updateRequest)).thenReturn(true);

        // 执行测试
        mockMvc.perform(put("/api/user/info")
                .contentType("application/json")
                .content("{\"nickname\":\"旅行者\",\"city\":\"上海\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));

        // 验证服务调用
        verify(userService, times(1)).updateUserInfo(1L, updateRequest);
    }

    /**
     * 测试修改密码
     */
    @Test
    public void testChangePassword() throws Exception {
        // 准备测试数据
        ChangePasswordRequest passwordRequest = new ChangePasswordRequest();
        passwordRequest.setOldPassword("123456");
        passwordRequest.setNewPassword("654321");
        passwordRequest.setConfirmPassword("654321");

        // 模拟服务调用
        when(userService.changePassword(1L, passwordRequest)).thenReturn(true);

        // 执行测试
        mockMvc.perform(put("/api/user/password")
                .contentType("application/json")
                .content("{\"oldPassword\":\"123456\",\"newPassword\":\"654321\",\"confirmPassword\":\"654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));

        // 验证服务调用
        verify(userService, times(1)).changePassword(1L, passwordRequest);
    }
}
