package com.travel.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用响应对象
 */
@Data
@NoArgsConstructor
public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     */
    public static <T> R<T> success() {
        return success(null);
    }

    /**
     * 成功
     */
    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    /**
     * 失败
     */
    public static <T> R<T> error() {
        return error(500, "error");
    }

    /**
     * 失败
     */
    public static <T> R<T> error(String msg) {
        return error(500, msg);
    }

    /**
     * 失败
     */
    public static <T> R<T> error(Integer code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    /**
     * 未登录
     */
    public static <T> R<T> unauth() {
        return error(401, "未登录");
    }

    /**
     * 无权限
     */
    public static <T> R<T> forbidden() {
        return error(403, "无权限");
    }

    /**
     * 参数错误
     */
    public static <T> R<T> badRequest(String msg) {
        return error(400, msg);
    }

    /**
     * 未找到
     */
    public static <T> R<T> notFound(String msg) {
        return error(404, msg);
    }
}
