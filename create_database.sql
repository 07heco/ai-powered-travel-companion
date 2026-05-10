-- =====================================================
-- AI-Powered Travel Companion 数据库初始化脚本
-- =====================================================
-- 数据库：travel_companion
-- 字符集：UTF8MB4
-- 排序规则：utf8mb4_unicode_ci
-- =====================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `travel_companion` 
DEFAULT CHARACTER SET = utf8mb4
DEFAULT COLLATE = utf8mb4_unicode_ci;

USE `travel_companion`;

-- =====================================================
-- 1. 用户表 (user)
-- =====================================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(100) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '加密后的密码',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `nickname` VARCHAR(100) DEFAULT NULL COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `bio` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_email` (`email`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- =====================================================
-- 2. 目的地表 (destination)
-- =====================================================
DROP TABLE IF EXISTS `destination`;
CREATE TABLE `destination` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '目的地ID',
    `name` VARCHAR(200) NOT NULL COMMENT '目的地名称',
    `country` VARCHAR(100) NOT NULL COMMENT '国家',
    `city` VARCHAR(100) NOT NULL COMMENT '城市',
    `province` VARCHAR(100) DEFAULT NULL COMMENT '省份',
    `description` TEXT COMMENT '描述',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图片',
    `images` JSON COMMENT '图片列表',
    `rating` DECIMAL(3,2) DEFAULT 0.00 COMMENT '评分',
    `review_count` INT DEFAULT 0 COMMENT '评论数',
    `view_count` INT DEFAULT 0 COMMENT '浏览次数',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `best_time` VARCHAR(100) DEFAULT NULL COMMENT '最佳旅游时间',
    `tags` JSON COMMENT '标签',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_country` (`country`),
    KEY `idx_city` (`city`),
    KEY `idx_rating` (`rating`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='目的地表';

-- =====================================================
-- 3. 景点表 (attraction)
-- =====================================================
DROP TABLE IF EXISTS `attraction`;
CREATE TABLE `attraction` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '景点ID',
    `destination_id` BIGINT NOT NULL COMMENT '目的地ID',
    `name` VARCHAR(200) NOT NULL COMMENT '景点名称',
    `description` TEXT COMMENT '描述',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图片',
    `images` JSON COMMENT '图片列表',
    `address` VARCHAR(300) DEFAULT NULL COMMENT '地址',
    `ticket_price` DECIMAL(10,2) DEFAULT 0.00 COMMENT '门票价格',
    `opening_hours` VARCHAR(200) DEFAULT NULL COMMENT '开放时间',
    `duration` VARCHAR(100) DEFAULT NULL COMMENT '建议游玩时长',
    `rating` DECIMAL(3,2) DEFAULT 0.00 COMMENT '评分',
    `review_count` INT DEFAULT 0 COMMENT '评论数',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `tags` JSON COMMENT '标签',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_destination_id` (`destination_id`),
    KEY `idx_rating` (`rating`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='景点表';

-- =====================================================
-- 4. 行程表 (trip)
-- =====================================================
DROP TABLE IF EXISTS `trip`;
CREATE TABLE `trip` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '行程ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '行程标题',
    `description` TEXT COMMENT '行程描述',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图片',
    `destination_id` BIGINT NOT NULL COMMENT '目的地ID',
    `start_date` DATE NOT NULL COMMENT '开始日期',
    `end_date` DATE NOT NULL COMMENT '结束日期',
    `total_days` INT NOT NULL COMMENT '总天数',
    `budget` DECIMAL(12,2) DEFAULT 0.00 COMMENT '预算',
    `is_public` TINYINT DEFAULT 0 COMMENT '是否公开：0-私有，1-公开',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-草稿，1-进行中，2-已完成',
    `view_count` INT DEFAULT 0 COMMENT '浏览次数',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_destination_id` (`destination_id`),
    KEY `idx_status` (`status`),
    KEY `idx_start_date` (`start_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行程表';

-- =====================================================
-- 5. 行程详情表 (trip_detail)
-- =====================================================
DROP TABLE IF EXISTS `trip_detail`;
CREATE TABLE `trip_detail` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '详情ID',
    `trip_id` BIGINT NOT NULL COMMENT '行程ID',
    `day_number` INT NOT NULL COMMENT '第几天',
    `title` VARCHAR(200) NOT NULL COMMENT '当天标题',
    `description` TEXT COMMENT '当天描述',
    `attraction_id` BIGINT DEFAULT NULL COMMENT '关联景点',
    `start_time` TIME DEFAULT NULL COMMENT '开始时间',
    `end_time` TIME DEFAULT NULL COMMENT '结束时间',
    `transportation` VARCHAR(100) DEFAULT NULL COMMENT '交通方式',
    `address` VARCHAR(300) DEFAULT NULL COMMENT '地址',
    `notes` TEXT COMMENT '备注',
    `order_num` INT DEFAULT 0 COMMENT '排序',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_trip_id` (`trip_id`),
    KEY `idx_day_number` (`day_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行程详情表';

-- =====================================================
-- 6. 预订表 (booking)
-- =====================================================
DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '预订ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `trip_id` BIGINT DEFAULT NULL COMMENT '关联行程ID',
    `booking_type` VARCHAR(50) NOT NULL COMMENT '预订类型：hotel-酒店，flight-机票，attraction-景点',
    `booking_no` VARCHAR(50) NOT NULL COMMENT '预订编号',
    `title` VARCHAR(200) NOT NULL COMMENT '预订标题',
    `description` TEXT COMMENT '预订描述',
    `checkin_date` DATE DEFAULT NULL COMMENT '入住/出发日期',
    `checkout_date` DATE DEFAULT NULL COMMENT '退房/到达日期',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总金额',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0-待支付，1-已支付，2-已取消，3-已完成',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_booking_no` (`booking_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_trip_id` (`trip_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预订表';

-- =====================================================
-- 7. 订单表 (order)
-- =====================================================
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `booking_id` BIGINT NOT NULL COMMENT '预订ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `total_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '订单金额',
    `discount_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠金额',
    `actual_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '实际支付金额',
    `coupon_id` BIGINT DEFAULT NULL COMMENT '使用的优惠券ID',
    `payment_method` VARCHAR(50) DEFAULT NULL COMMENT '支付方式',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0-待支付，1-已支付，2-已退款，3-已取消',
    `paid_at` DATETIME DEFAULT NULL COMMENT '支付时间',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_booking_id` (`booking_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- =====================================================
-- 8. 支付表 (payment)
-- =====================================================
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '支付ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `payment_no` VARCHAR(50) NOT NULL COMMENT '支付流水号',
    `payment_method` VARCHAR(50) NOT NULL COMMENT '支付方式：alipay-支付宝，wechat-微信，balance-余额',
    `amount` DECIMAL(12,2) NOT NULL COMMENT '支付金额',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0-待支付，1-支付成功，2-支付失败，3-已退款',
    `paid_at` DATETIME DEFAULT NULL COMMENT '支付时间',
    `transaction_id` VARCHAR(100) DEFAULT NULL COMMENT '第三方交易号',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_no` (`payment_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付表';

-- =====================================================
-- 9. 钱包表 (wallet)
-- =====================================================
DROP TABLE IF EXISTS `wallet`;
CREATE TABLE `wallet` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '钱包ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `balance` DECIMAL(12,2) DEFAULT 0.00 COMMENT '余额',
    `frozen_balance` DECIMAL(12,2) DEFAULT 0.00 COMMENT '冻结余额',
    `total_income` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总收入',
    `total_expense` DECIMAL(12,2) DEFAULT 0.00 COMMENT '总支出',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-冻结，1-正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='钱包表';

-- =====================================================
-- 10. 优惠券表 (coupon)
-- =====================================================
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '描述',
    `type` TINYINT NOT NULL COMMENT '类型：1-满减券，2-折扣券，3-无门槛',
    `discount_value` DECIMAL(10,2) NOT NULL COMMENT '优惠值（金额或折扣）',
    `min_amount` DECIMAL(12,2) DEFAULT 0.00 COMMENT '最低使用金额',
    `max_discount` DECIMAL(12,2) DEFAULT NULL COMMENT '最大优惠金额',
    `valid_from` DATETIME NOT NULL COMMENT '有效期开始',
    `valid_to` DATETIME NOT NULL COMMENT '有效期结束',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0-未使用，1-已使用，2-已过期',
    `used_at` DATETIME DEFAULT NULL COMMENT '使用时间',
    `order_id` BIGINT DEFAULT NULL COMMENT '使用的订单ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_valid_to` (`valid_to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券表';

-- =====================================================
-- 11. 消息表 (message)
-- =====================================================
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `type` VARCHAR(50) NOT NULL COMMENT '消息类型：system-系统，order-订单，booking-预订',
    `title` VARCHAR(200) NOT NULL COMMENT '消息标题',
    `content` TEXT COMMENT '消息内容',
    `related_id` BIGINT DEFAULT NULL COMMENT '关联ID（订单ID、预订ID等）',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
    `read_at` DATETIME DEFAULT NULL COMMENT '阅读时间',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_read` (`is_read`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息表';

-- =====================================================
-- 12. AI计划表 (ai_plan)
-- =====================================================
DROP TABLE IF EXISTS `ai_plan`;
CREATE TABLE `ai_plan` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '计划ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `destination` VARCHAR(200) NOT NULL COMMENT '目的地',
    `start_date` DATE NOT NULL COMMENT '开始日期',
    `end_date` DATE NOT NULL COMMENT '结束日期',
    `preferences` JSON COMMENT '用户偏好',
    `plan_content` TEXT NOT NULL COMMENT 'AI生成的计划内容',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0-生成中，1-已完成，2-已保存',
    `trip_id` BIGINT DEFAULT NULL COMMENT '关联的行程ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_destination` (`destination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI计划表';

-- =====================================================
-- 13. 旅行伴侣表 (travel_mate)
-- =====================================================
DROP TABLE IF EXISTS `travel_mate`;
CREATE TABLE `travel_mate` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '伴侣ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `name` VARCHAR(100) NOT NULL COMMENT '伴侣名称',
    `personality` VARCHAR(200) DEFAULT NULL COMMENT '性格特征',
    `expertise` VARCHAR(200) DEFAULT NULL COMMENT '专长领域',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='旅行伴侣表';

-- =====================================================
-- 14. 优惠活动表 (deal)
-- =====================================================
DROP TABLE IF EXISTS `deal`;
CREATE TABLE `deal` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '活动ID',
    `title` VARCHAR(200) NOT NULL COMMENT '活动标题',
    `description` TEXT COMMENT '活动描述',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图片',
    `deal_type` VARCHAR(50) NOT NULL COMMENT '活动类型：hotel-酒店，flight-机票，attraction-景点',
    `discount_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '折扣率',
    `discount_amount` DECIMAL(12,2) DEFAULT NULL COMMENT '优惠金额',
    `valid_from` DATETIME NOT NULL COMMENT '开始时间',
    `valid_to` DATETIME NOT NULL COMMENT '结束时间',
    `max_usage` INT DEFAULT NULL COMMENT '最大使用次数',
    `current_usage` INT DEFAULT 0 COMMENT '当前使用次数',
    `priority` INT DEFAULT 0 COMMENT '优先级',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-未开始，1-进行中，2-已结束',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_deal_type` (`deal_type`),
    KEY `idx_status` (`status`),
    KEY `idx_valid_from` (`valid_from`),
    KEY `idx_valid_to` (`valid_to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠活动表';

-- =====================================================
-- 插入初始测试数据
-- =====================================================

-- 插入测试用户
INSERT INTO `user` (`username`, `password`, `phone`, `email`, `nickname`, `status`) VALUES
('testuser', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '13800138000', 'test@example.com', '测试用户', 1);

-- 插入测试目的地
INSERT INTO `destination` (`name`, `country`, `city`, `description`, `rating`, `status`) VALUES
('北京故宫', '中国', '北京', '北京故宫是中国明清两代的皇家宫殿，旧称为紫禁城，位于北京中轴线的中心，是中国古代宫廷建筑之精华。', 4.8, 1),
('上海外滩', '中国', '上海', '外滩是上海市中心的一个区域，位于黄浦区的东部，全长1.5公里，周围环绕着52幢古典复兴风格的建筑。', 4.7, 1);

-- 插入测试景点
INSERT INTO `attraction` (`destination_id`, `name`, `description`, `ticket_price`, `rating`, `status`) VALUES
(1, '太和殿', '太和殿俗称金銮殿，为中国古代宫殿建筑之精华，东方三大殿之一。', 60.00, 4.9, 1),
(1, '干清宫', '干清宫是内廷正殿，是紫禁城内廷后三宫之一，位于中轴线上。', 0.00, 4.5, 1);

-- 插入测试优惠券
INSERT INTO `wallet` (`user_id`, `balance`, `status`) VALUES
(1, 1000.00, 1);

INSERT INTO `coupon` (`user_id`, `name`, `description`, `type`, `discount_value`, `min_amount`, `valid_from`, `valid_to`, `status`) VALUES
(1, '新用户专享券', '新用户注册专享，满200减50', 1, 50.00, 200.00, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 0);

-- =====================================================
-- 数据库初始化完成
-- =====================================================
