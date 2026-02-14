package com.travel.user.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.travel.user.constant.RedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 微信授权工具类
 */
@Slf4j
@Component
public class WechatUtil {
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 微信小程序AppId
     */
    @Value("${wechat.app-id:your-app-id}")
    private String appId;
    
    /**
     * 微信小程序AppSecret
     */
    @Value("${wechat.app-secret:your-app-secret}")
    private String appSecret;
    
    /**
     * 获取微信openid和session_key
     * @param code 微信授权code
     * @return 包含openid和session_key的JSONObject
     */
    public JSONObject getWechatAuthInfo(String code) {
        try {
            // 检查Redis缓存
            String cacheKey = RedisKeyConstant.getWechatAuthKey(code);
            Object cachedValue = redisTemplate.opsForValue().get(cacheKey);
            if (cachedValue != null) {
                return JSONUtil.parseObj(cachedValue.toString());
            }
            
            // 构建请求URL
            String url = StrUtil.format(
                    "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code",
                    appId, appSecret, code
            );
            
            // 发送请求
            HttpResponse response = HttpRequest.get(url).execute();
            if (!response.isOk()) {
                log.error("微信授权失败: {}", response.body());
                return null;
            }
            
            // 解析响应
            JSONObject result = JSONUtil.parseObj(response.body());
            if (result.containsKey("errcode")) {
                int errCode = result.getInt("errcode");
                if (errCode != 0) {
                    log.error("微信授权失败: errcode={}, errmsg={}", errCode, result.getStr("errmsg"));
                    return null;
                }
            }
            
            // 缓存结果
            redisTemplate.opsForValue().set(cacheKey, result.toString(), 10, TimeUnit.MINUTES);
            
            return result;
        } catch (Exception e) {
            log.error("微信授权异常: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 获取微信用户信息
     * @param accessToken 访问令牌
     * @param openid 用户openid
     * @return 微信用户信息
     */
    public JSONObject getWechatUserInfo(String accessToken, String openid) {
        try {
            // 构建请求URL
            String url = StrUtil.format(
                    "https://api.weixin.qq.com/sns/userinfo?access_token={}&openid={}&lang=zh_CN",
                    accessToken, openid
            );
            
            // 发送请求
            HttpResponse response = HttpRequest.get(url).execute();
            if (!response.isOk()) {
                log.error("获取微信用户信息失败: {}", response.body());
                return null;
            }
            
            // 解析响应
            JSONObject result = JSONUtil.parseObj(response.body());
            if (result.containsKey("errcode")) {
                int errCode = result.getInt("errcode");
                if (errCode != 0) {
                    log.error("获取微信用户信息失败: errcode={}, errmsg={}", errCode, result.getStr("errmsg"));
                    return null;
                }
            }
            
            return result;
        } catch (Exception e) {
            log.error("获取微信用户信息异常: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 生成微信授权URL
     * @param redirectUri 重定向地址
     * @param state 状态参数
     * @return 微信授权URL
     */
    public String generateWechatAuthUrl(String redirectUri, String state) {
        return StrUtil.format(
                "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope=snsapi_userinfo&state={}#wechat_redirect",
                appId, redirectUri, state
        );
    }
}
