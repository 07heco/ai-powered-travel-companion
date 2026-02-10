package com.travel.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.common.utils.JWTUtil;
import com.travel.common.utils.RedisUtil;
import com.travel.user.dao.UserDao;
import com.travel.user.entity.User;
import com.travel.user.service.UserService;
import com.travel.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 验证码前缀
     */
    private static final String CODE_PREFIX = "travel:code:";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "travel:token:";

    /**
     * 用户登录
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        // 验证手机号
        if (StrUtil.isBlank(request.getPhone())) {
            throw new RuntimeException("手机号不能为空");
        }

        // 验证密码
        if (StrUtil.isBlank(request.getPassword())) {
            throw new RuntimeException("密码不能为空");
        }

        // 根据手机号查询用户
        User user = userDao.selectByPhone(request.getPhone());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 验证用户状态
        if (user.getStatus() == 0) {
            throw new RuntimeException("用户已禁用");
        }

        // 生成JWT令牌
        String accessToken = generateToken(user);
        String refreshToken = generateRefreshToken(user);

        // 缓存令牌
        redisUtil.set(TOKEN_PREFIX + user.getId(), accessToken, 24, TimeUnit.HOURS);

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUserId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());

        return response;
    }

    /**
     * 用户注册
     */
    @Override
    public RegisterResponse register(RegisterRequest request) {
        // 验证手机号
        if (StrUtil.isBlank(request.getPhone())) {
            throw new RuntimeException("手机号不能为空");
        }

        // 验证验证码
        if (StrUtil.isBlank(request.getCode())) {
            throw new RuntimeException("验证码不能为空");
        }

        // 验证密码
        if (StrUtil.isBlank(request.getPassword())) {
            throw new RuntimeException("密码不能为空");
        }

        // 验证确认密码
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 验证验证码
        String codeKey = CODE_PREFIX + request.getPhone();
        String storedCode = (String) redisUtil.get(codeKey);
        if (!request.getCode().equals(storedCode)) {
            throw new RuntimeException("验证码错误");
        }

        // 检查用户是否已存在
        User existingUser = userDao.selectByPhone(request.getPhone());
        if (existingUser != null) {
            throw new RuntimeException("用户已存在");
        }

        // 创建新用户
        User user = new User();
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname("用户" + request.getPhone().substring(7));
        user.setAvatar("https://neeko-copilot.bytedance.net/api/text2image?prompt=user%20avatar&size=128x128");
        user.setGender(0);
        user.setStatus(1);
        user.setType(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 保存用户
        if (!save(user)) {
            throw new RuntimeException("注册失败");
        }

        // 删除验证码
        redisUtil.delete(codeKey);

        // 构建响应
        RegisterResponse response = new RegisterResponse();
        response.setUserId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());

        return response;
    }

    /**
     * 发送验证码
     */
    @Override
    public boolean sendCode(String phone, String type) {
        // 验证手机号
        if (StrUtil.isBlank(phone)) {
            throw new RuntimeException("手机号不能为空");
        }

        // 生成验证码
        String code = RandomUtil.randomNumbers(6);

        // 缓存验证码
        String key = CODE_PREFIX + phone;
        redisUtil.set(key, code, 5, TimeUnit.MINUTES);

        // 模拟发送验证码
        log.info("向手机号 {} 发送 {} 验证码: {}", phone, type, code);

        return true;
    }

    /**
     * 获取用户信息
     */
    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        // 查询用户
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 构建响应
        UserInfoResponse response = new UserInfoResponse();
        response.setId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        response.setGender(user.getGender());
        response.setBirthday(user.getBirthday());
        response.setEmail(user.getEmail());
        response.setCity(user.getCity());
        response.setBio(user.getBio());
        response.setType(user.getType());
        response.setCreateTime(user.getCreateTime());

        return response;
    }

    /**
     * 更新用户信息
     */
    @Override
    public boolean updateUserInfo(Long userId, UpdateUserInfoRequest request) {
        // 查询用户
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 更新用户信息
        if (StrUtil.isNotBlank(request.getNickname())) {
            user.setNickname(request.getNickname());
        }
        if (StrUtil.isNotBlank(request.getAvatar())) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getBirthday() != null) {
            user.setBirthday(request.getBirthday());
        }
        if (StrUtil.isNotBlank(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (StrUtil.isNotBlank(request.getCity())) {
            user.setCity(request.getCity());
        }
        if (StrUtil.isNotBlank(request.getBio())) {
            user.setBio(request.getBio());
        }

        user.setUpdateTime(LocalDateTime.now());

        // 保存更新
        return updateById(user);
    }

    /**
     * 修改密码
     */
    @Override
    public boolean changePassword(Long userId, ChangePasswordRequest request) {
        // 验证旧密码
        if (StrUtil.isBlank(request.getOldPassword())) {
            throw new RuntimeException("旧密码不能为空");
        }

        // 验证新密码
        if (StrUtil.isBlank(request.getNewPassword())) {
            throw new RuntimeException("新密码不能为空");
        }

        // 验证确认密码
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 查询用户
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("旧密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdateTime(LocalDateTime.now());

        // 保存更新
        return updateById(user);
    }

    /**
     * 登出
     */
    @Override
    public boolean logout(String token) {
        // 解析token
        String userId = JWTUtil.getSubject(token);
        if (StrUtil.isBlank(userId)) {
            throw new RuntimeException("无效的令牌");
        }

        // 删除缓存的token
        redisUtil.delete(TOKEN_PREFIX + userId);

        return true;
    }

    /**
     * 生成访问令牌
     */
    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("phone", user.getPhone());
        claims.put("type", user.getType());
        return JWTUtil.generateToken(user.getId().toString(), claims);
    }

    /**
     * 生成刷新令牌
     */
    private String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        return JWTUtil.generateToken(user.getId().toString() + "_refresh", claims);
    }
}
