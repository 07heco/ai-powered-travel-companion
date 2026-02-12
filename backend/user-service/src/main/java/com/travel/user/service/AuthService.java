package com.travel.user.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.travel.user.dao.UserDao;
import com.travel.user.dao.UserThirdPartyDao;
import com.travel.user.entity.User;
import com.travel.user.entity.UserThirdParty;
import com.travel.user.utils.WechatUtil;
import com.travel.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 认证服务
 */
@Slf4j
@Service
public class AuthService {
    
    @Resource
    private UserDao userDao;
    
    @Resource
    private UserThirdPartyDao userThirdPartyDao;
    
    @Resource
    private SmsService smsService;
    
    @Resource
    private WechatUtil wechatUtil;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
    /**
     * 手机号登录
     * @param request 登录请求
     * @return 登录响应
     */
    public LoginResponse phoneLogin(LoginRequest request) {
        String phone = request.getPhone();
        String password = request.getPassword();
        String code = request.getCode();
        
        // 验证码登录
        if (code != null && !code.isEmpty()) {
            // 验证验证码
            boolean valid = smsService.validateCode(phone, code, "login");
            if (!valid) {
                throw new RuntimeException("验证码错误或已过期");
            }
        } 
        // 密码登录
        else if (password != null && !password.isEmpty()) {
            // 验证密码
            User user = userDao.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getPhone, phone)
                    .eq(User::getStatus, 1)
                    .eq(User::getDeleted, 0)
            );
            
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }
            
   // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        } else {
            throw new RuntimeException("请提供密码或验证码");
        }
        
        // 登录认证
        User user = userDao.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone)
                .eq(User::getStatus, 1)
                .eq(User::getDeleted, 0)
        );
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // Sa-Token登录
        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();
        
        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setUserId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        
        log.info("用户登录成功: userId={}, phone={}", user.getId(), phone);
        return response;
    }
    
    /**
     * 微信登录
     * @param code 微信授权code
     * @return 登录响应
     */
    public LoginResponse wechatLogin(String code) {
        // 获取微信认证信息
        JSONObject authInfo = wechatUtil.getWechatAuthInfo(code);
        if (authInfo == null) {
            throw new RuntimeException("微信授权失败");
        }
        
        String openid = authInfo.getStr("openid");
        String unionid = authInfo.getStr("unionid");
        
        // 查找微信账号关联
        UserThirdParty thirdParty = userThirdPartyDao.selectByThirdTypeAndOpenid("wechat", openid);
        
        // 未关联用户，创建新用户
        if (thirdParty == null) {
            return wechatRegister(openid, unionid);
        }
        
        // 已关联用户，直接登录
        User user = userDao.selectById(thirdParty.getUserId());
        if (user == null || user.getStatus() == 0 || user.getDeleted() == 1) {
            throw new RuntimeException("用户不存在或已禁用");
        }
        
        // Sa-Token登录
        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();
        
        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setUserId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        
        log.info("微信登录成功: userId={}, openid={}", user.getId(), openid);
        return response;
    }
    
    /**
     * 手机号注册
     * @param request 注册请求
     * @return 注册响应
     */
    @Transactional
    public RegisterResponse phoneRegister(RegisterRequest request) {
        String phone = request.getPhone();
        String code = request.getCode();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();
        
        // 验证密码
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        
        // 验证验证码
        boolean valid = smsService.validateCode(phone, code, "register");
        if (!valid) {
            throw new RuntimeException("验证码错误或已过期");
        }
        
        // 检查手机号是否已注册
        User existingUser = userDao.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone)
        );
        
        if (existingUser != null) {
            throw new RuntimeException("手机号已注册");
        }
        
        // 创建用户
        User user = new User();
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname("用户" + phone.substring(7));
        user.setAvatar("https://neeko-copilot.bytedance.net/api/text2image?prompt=user%20avatar&size=128x128");
        user.setStatus(1);
        user.setDeleted(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        userDao.insert(user);
        
        // Sa-Token登录
        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();
        
        // 构建响应
        RegisterResponse response = new RegisterResponse();
        response.setUserId(user.getId());
        response.setPhone(user.getPhone());
        response.setNickname(user.getNickname());
        
        log.info("用户注册成功: userId={}, phone={}", user.getId(), phone);
        return response;
    }
    
    /**
     * 微信注册
     * @param openid 微信openid
     * @param unionid 微信unionid
     * @return 登录响应
     */
    @Transactional
    public LoginResponse wechatRegister(String openid, String unionid) {
        // 创建用户
        User user = new User();
        user.setNickname("微信用户" + openid.substring(0, 6));
        user.setStatus(1);
        user.setDeleted(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        userDao.insert(user);
        
        // 关联微信账号
        UserThirdParty thirdParty = new UserThirdParty();
        thirdParty.setUserId(user.getId());
        thirdParty.setThirdType("wechat");
        thirdParty.setThirdOpenid(openid);
        thirdParty.setThirdUnionid(unionid);
        thirdParty.setCreateTime(LocalDateTime.now());
        thirdParty.setUpdateTime(LocalDateTime.now());
        
        userThirdPartyDao.insert(thirdParty);
        
        // Sa-Token登录
        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();
        
        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setUserId(user.getId());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        
        log.info("微信注册成功: userId={}, openid={}", user.getId(), openid);
        return response;
    }
    
    /**
     * 登出
     */
    public void logout() {
        StpUtil.logout();
        log.info("用户登出成功");
    }
    
    /**
     * 绑定微信账号
     * @param userId 用户ID
     * @param code 微信授权code
     * @return 是否绑定成功
     */
    @Transactional
    public boolean bindWechat(Long userId, String code) {
        // 获取微信认证信息
        JSONObject authInfo = wechatUtil.getWechatAuthInfo(code);
        if (authInfo == null) {
            throw new RuntimeException("微信授权失败");
        }
        
        String openid = authInfo.getStr("openid");
        String unionid = authInfo.getStr("unionid");
        
        // 检查是否已绑定
        UserThirdParty existing = userThirdPartyDao.selectByThirdTypeAndOpenid("wechat", openid);
        if (existing != null) {
            throw new RuntimeException("该微信账号已绑定其他用户");
        }
        
        // 绑定微信账号
        UserThirdParty thirdParty = new UserThirdParty();
        thirdParty.setUserId(userId);
        thirdParty.setThirdType("wechat");
        thirdParty.setThirdOpenid(openid);
        thirdParty.setThirdUnionid(unionid);
        thirdParty.setCreateTime(LocalDateTime.now());
        thirdParty.setUpdateTime(LocalDateTime.now());
        
        userThirdPartyDao.insert(thirdParty);
        
        log.info("微信账号绑定成功: userId={}, openid={}", userId, openid);
        return true;
    }
}
