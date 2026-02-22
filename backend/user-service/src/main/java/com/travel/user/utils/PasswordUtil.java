package com.travel.user.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码工具类
 */
@Slf4j
public class PasswordUtil {

    /**
     * 密码强度等级
     */
    public enum StrengthLevel {
        WEAK,       // 弱
        MEDIUM,     // 中等
        STRONG      // 强
    }

    /**
     * 检查密码强度
     * @param password 密码
     * @return 强度等级
     */
    public static StrengthLevel checkPasswordStrength(String password) {
        if (password == null || password.length() < 6) {
            return StrengthLevel.WEAK;
        }

        int score = 0;

        // 长度检查
        if (password.length() >= 8) {
            score++;
        }
        if (password.length() >= 12) {
            score++;
        }

        // 包含数字
        if (Pattern.matches(".*\\d.*", password)) {
            score++;
        }

        // 包含小写字母
        if (Pattern.matches(".*[a-z].*", password)) {
            score++;
        }

        // 包含大写字母
        if (Pattern.matches(".*[A-Z].*", password)) {
            score++;
        }

        // 包含特殊字符
        if (Pattern.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':,.<>?].*", password)) {
            score++;
        }

        // 根据分数返回强度等级
        if (score < 3) {
            return StrengthLevel.WEAK;
        } else if (score < 5) {
            return StrengthLevel.MEDIUM;
        } else {
            return StrengthLevel.STRONG;
        }
    }

    /**
     * 验证密码是否符合要求
     * @param password 密码
     * @return 是否符合要求
     */
    public static boolean validatePassword(String password) {
        StrengthLevel level = checkPasswordStrength(password);
        return level != StrengthLevel.WEAK;
    }

    /**
     * 获取密码强度提示
     * @param password 密码
     * @return 强度提示
     */
    public static String getPasswordStrengthTip(String password) {
        StrengthLevel level = checkPasswordStrength(password);
        switch (level) {
            case WEAK:
                return "密码强度弱，请包含至少8个字符，包括字母和数字";
            case MEDIUM:
                return "密码强度中等，建议添加特殊字符";
            case STRONG:
                return "密码强度强";
            default:
                return "密码强度检查失败";
        }
    }
}
