-- 创建 travel_companion 数据库
CREATE DATABASE IF NOT EXISTS travel_companion DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用 travel_companion 数据库
USE travel_companion;

-- 创建 user 表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    phone VARCHAR(20) COMMENT '手机号',
    password VARCHAR(255) COMMENT '密码（BCrypt加密）',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像',
    gender TINYINT DEFAULT 0 COMMENT '性别（0-未知，1-男，2-女）',
    birthday DATETIME COMMENT '生日',
    email VARCHAR(100) COMMENT '邮箱',
    city VARCHAR(50) COMMENT '城市',
    bio VARCHAR(500) COMMENT '个人简介',
    type TINYINT DEFAULT 0 COMMENT '用户类型（0-普通用户，1-旅行达人，2-商家）',
    status TINYINT DEFAULT 1 COMMENT '状态（0-禁用，1-启用）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除（0-未删除，1-已删除）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_phone (phone),
    KEY idx_status (status),
    KEY idx_type (type),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 创建 user_third_party 表
CREATE TABLE IF NOT EXISTS user_third_party (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    third_type VARCHAR(20) NOT NULL COMMENT '第三方类型（wechat等）',
    third_openid VARCHAR(100) NOT NULL COMMENT '第三方OpenID',
    third_unionid VARCHAR(100) COMMENT '第三方UnionID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_third_openid (third_type, third_openid),
    KEY idx_user_id (user_id),
    CONSTRAINT fk_user_third_party_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='第三方账号关联表';
