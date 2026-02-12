package com.travel.user.constant;

/**
 * Redis键常量
 */
public class RedisKeyConstant {
    
    /**
     * 短信验证码键前缀
     */
    private static final String SMS_CODE_PREFIX = "sms:code:";
    
    /**
     * 短信发送锁键前缀
     */
    private static final String SMS_LOCK_PREFIX = "sms:lock:";
    
    /**
     * 用户会话键前缀
     */
    private static final String USER_SESSION_PREFIX = "user:session:";
    
    /**
     * 微信授权缓存键前缀
     */
    private static final String WECHAT_AUTH_PREFIX = "wechat:auth:";
    
    /**
     * 获取短信验证码键
     * @param phone 手机号
     * @param type 类型（register/login）
     */
    public static String getSmsCodeKey(String phone, String type) {
        return SMS_CODE_PREFIX + phone + ":" + type;
    }
    
    /**
     * 获取短信发送锁键
     * @param phone 手机号
     * @param type 类型（register/login）
     */
    public static String getSmsLockKey(String phone, String type) {
        return SMS_LOCK_PREFIX + phone + ":" + type;
    }
    
    /**
     * 获取用户会话键
     * @param userId 用户ID
     */
    public static String getUserSessionKey(Long userId) {
        return USER_SESSION_PREFIX + userId;
    }
    
    /**
     * 获取微信授权缓存键
     * @param code 微信授权码
     */
    public static String getWechatAuthKey(String code) {
        return WECHAT_AUTH_PREFIX + code;
    }
}
