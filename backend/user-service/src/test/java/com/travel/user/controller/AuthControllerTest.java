package com.travel.user.controller;

import com.travel.common.vo.R;
import com.travel.user.service.AuthService;
import com.travel.user.service.SmsService;
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
 * AuthController单元测试
 */
public class AuthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private SmsService smsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    /**
     * 测试微信登录
     */
    @Test
    public void testWechatLogin() throws Exception {
        // 准备测试数据
        String code = "test-code";
        LoginResponse expectedResponse = new LoginResponse();
        expectedResponse.setAccessToken("test-token");
        expectedResponse.setUserId(1L);
        expectedResponse.setNickname("微信用户");

        // 模拟服务调用
        when(authService.wechatLogin(code)).thenReturn(expectedResponse);

        // 执行测试
        mockMvc.perform(get("/api/auth/wechat/login")
                .param("code", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("test-token"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.nickname").value("微信用户"));

        // 验证服务调用
        verify(authService, times(1)).wechatLogin(code);
    }

    /**
     * 测试手机号登录
     */
    @Test
    public void testPhoneLogin() throws Exception {
        // 准备测试数据
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("13800138000");
        loginRequest.setPassword("123456");

        LoginResponse expectedResponse = new LoginResponse();
        expectedResponse.setAccessToken("test-token");
        expectedResponse.setUserId(1L);
        expectedResponse.setPhone("13800138000");

        // 模拟服务调用
        when(authService.phoneLogin(loginRequest)).thenReturn(expectedResponse);

        // 执行测试
        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content("{\"phone\":\"13800138000\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("test-token"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.phone").value("13800138000"));

        // 验证服务调用
        verify(authService, times(1)).phoneLogin(any(LoginRequest.class));
    }

    /**
     * 测试手机号注册
     */
    @Test
    public void testPhoneRegister() throws Exception {
        // 准备测试数据
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13800138000");
        registerRequest.setCode("123456");
        registerRequest.setPassword("123456");
        registerRequest.setConfirmPassword("123456");

        RegisterResponse expectedResponse = new RegisterResponse();
        expectedResponse.setUserId(1L);
        expectedResponse.setPhone("13800138000");
        expectedResponse.setNickname("用户8000");

        // 模拟服务调用
        when(authService.phoneRegister(registerRequest)).thenReturn(expectedResponse);

        // 执行测试
        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content("{\"phone\":\"13800138000\",\"code\":\"123456\",\"password\":\"123456\",\"confirmPassword\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.phone").value("13800138000"))
                .andExpect(jsonPath("$.data.nickname").value("用户8000"));

        // 验证服务调用
        verify(authService, times(1)).phoneRegister(any(RegisterRequest.class));
    }

    /**
     * 测试发送验证码
     */
    @Test
    public void testSendCode() throws Exception {
        // 准备测试数据
        String phone = "13800138000";
        String type = "register";

        // 模拟服务调用
        when(smsService.sendCode(phone, type)).thenReturn(true);

        // 执行测试
        mockMvc.perform(post("/api/auth/send-code")
                .param("phone", phone)
                .param("type", type))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));

        // 验证服务调用
        verify(smsService, times(1)).sendCode(phone, type);
    }

    /**
     * 测试登出
     */
    @Test
    public void testLogout() throws Exception {
        // 模拟服务调用
        doNothing().when(authService).logout();

        // 执行测试
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));

        // 验证服务调用
        verify(authService, times(1)).logout();
    }
}
