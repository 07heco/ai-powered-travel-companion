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

-- 创建 ai_plan 表
CREATE TABLE IF NOT EXISTS ai_plan (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    plan_name VARCHAR(100) COMMENT '计划名称',
    destination VARCHAR(100) COMMENT '目的地',
    start_date DATETIME COMMENT '开始日期',
    end_date DATETIME COMMENT '结束日期',
    plan_content TEXT COMMENT '计划内容',
    status VARCHAR(20) COMMENT '状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_destination (destination),
    KEY idx_status (status),
    CONSTRAINT fk_ai_plan_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI旅行计划表';

-- 创建 travel_mate 表
CREATE TABLE IF NOT EXISTS travel_mate (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    name VARCHAR(50) COMMENT '名称',
    avatar VARCHAR(255) COMMENT '头像',
    personality VARCHAR(255) COMMENT '个性',
    expertise VARCHAR(255) COMMENT '专业知识',
    status VARCHAR(20) COMMENT '状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_status (status),
    CONSTRAINT fk_travel_mate_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='旅游伙伴表';

-- 创建 ar_landmark 表
CREATE TABLE IF NOT EXISTS ar_landmark (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name VARCHAR(100) COMMENT '名称',
    english_name VARCHAR(200) COMMENT '英文名称',
    category VARCHAR(50) COMMENT '分类',
    rating DOUBLE COMMENT '评分',
    description TEXT COMMENT '描述',
    history TEXT COMMENT '历史',
    open_time VARCHAR(100) COMMENT '开放时间',
    ticket_price VARCHAR(50) COMMENT '门票价格',
    highlights VARCHAR(255) COMMENT '亮点',
    tips VARCHAR(255) COMMENT '提示',
    location VARCHAR(255) COMMENT '位置',
    latitude DOUBLE COMMENT '纬度',
    longitude DOUBLE COMMENT '经度',
    image_url VARCHAR(255) COMMENT '图片URL',
    recognition_keywords VARCHAR(255) COMMENT '识别关键词',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_name (name),
    KEY idx_category (category),
    KEY idx_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AR景点信息表';

-- 创建 budget 表
CREATE TABLE IF NOT EXISTS budget (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    trip_name VARCHAR(100) COMMENT '旅行名称',
    total_budget DOUBLE COMMENT '总预算',
    spent_amount DOUBLE COMMENT '已花费金额',
    start_date VARCHAR(50) COMMENT '开始日期',
    end_date VARCHAR(50) COMMENT '结束日期',
    destination VARCHAR(100) COMMENT '目的地',
    status VARCHAR(20) COMMENT '状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_destination (destination),
    KEY idx_status (status),
    CONSTRAINT fk_budget_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预算表';

-- 创建 expense 表
CREATE TABLE IF NOT EXISTS expense (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    budget_id BIGINT NOT NULL COMMENT '预算ID',
    category VARCHAR(50) COMMENT '分类',
    amount DOUBLE COMMENT '金额',
    description VARCHAR(255) COMMENT '描述',
    date VARCHAR(50) COMMENT '日期',
    payment_method VARCHAR(50) COMMENT '支付方式',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_budget_id (budget_id),
    KEY idx_category (category),
    CONSTRAINT fk_expense_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_expense_budget_id FOREIGN KEY (budget_id) REFERENCES budget (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支出记录表';

-- 创建 travel_mate_match 表
CREATE TABLE IF NOT EXISTS travel_mate_match (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    matched_user_id BIGINT NOT NULL COMMENT '匹配用户ID',
    destination VARCHAR(100) COMMENT '目的地',
    travel_date VARCHAR(50) COMMENT '旅行日期',
    travel_style VARCHAR(100) COMMENT '旅行风格',
    interests VARCHAR(255) COMMENT '兴趣爱好',
    match_score INT COMMENT '匹配分数',
    status VARCHAR(20) COMMENT '状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_matched_user_id (matched_user_id),
    KEY idx_destination (destination),
    KEY idx_match_score (match_score),
    KEY idx_status (status),
    CONSTRAINT fk_travel_mate_match_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='旅伴匹配记录表';

-- 创建 recruit_post 表
CREATE TABLE IF NOT EXISTS recruit_post (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    author_name VARCHAR(50) COMMENT '作者名称',
    destination VARCHAR(100) COMMENT '目的地',
    travel_date VARCHAR(50) COMMENT '旅行日期',
    duration VARCHAR(50) COMMENT '时长',
    current_members INT COMMENT '当前成员数',
    max_members INT COMMENT '最大成员数',
    tags VARCHAR(255) COMMENT '标签',
    budget VARCHAR(100) COMMENT '预算',
    description TEXT COMMENT '描述',
    status VARCHAR(20) COMMENT '状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_destination (destination),
    KEY idx_status (status),
    CONSTRAINT fk_recruit_post_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招募帖表';
