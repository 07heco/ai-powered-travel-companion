-- 创建预订主表
CREATE TABLE IF NOT EXISTS `booking` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `trip_id` BIGINT NULL,
  `booking_type` VARCHAR(50) NOT NULL COMMENT '预订类型（flight/hotel/ticket）',
  `booking_name` VARCHAR(255) NOT NULL COMMENT '预订名称',
  `total_price` DECIMAL(10,2) NOT NULL COMMENT '总价格',
  `status` VARCHAR(20) NOT NULL COMMENT '预订状态（pending/confirmed/cancelled）',
  `payment_status` VARCHAR(20) NOT NULL COMMENT '支付状态（unpaid/paid/refunded）',
  `check_in_date` DATETIME NULL COMMENT '入住/使用开始日期',
  `check_out_date` DATETIME NULL COMMENT '退房/使用结束日期',
  `adult_count` INT NULL COMMENT '成人数量',
  `child_count` INT NULL COMMENT '儿童数量',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_trip_id` (`trip_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_payment_status` (`payment_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预订主表';

-- 创建订单表
CREATE TABLE IF NOT EXISTS `orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `booking_id` BIGINT NOT NULL,
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
  `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
  `status` VARCHAR(20) NOT NULL COMMENT '订单状态（pending/paid/cancelled）',
  `payment_method` VARCHAR(50) NULL COMMENT '支付方式（alipay/wechat/card）',
  `transaction_id` VARCHAR(100) NULL COMMENT '交易ID',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_order_no` (`order_no`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_booking_id` (`booking_id`),
  INDEX `idx_status` (`status`),
  CONSTRAINT `fk_order_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 创建航班预订详情表
CREATE TABLE IF NOT EXISTS `flight_booking` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `booking_id` BIGINT NOT NULL,
  `flight_no` VARCHAR(20) NOT NULL COMMENT '航班号',
  `airline` VARCHAR(100) NOT NULL COMMENT '航空公司',
  `departure_airport` VARCHAR(100) NOT NULL COMMENT '出发机场',
  `arrival_airport` VARCHAR(100) NOT NULL COMMENT '到达机场',
  `departure_time` DATETIME NOT NULL COMMENT '出发时间',
  `arrival_time` DATETIME NOT NULL COMMENT '到达时间',
  `class_type` VARCHAR(50) NOT NULL COMMENT '舱位类型',
  `passenger_info` JSON NOT NULL COMMENT '乘客信息',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_booking_id` (`booking_id`),
  INDEX `idx_flight_no` (`flight_no`),
  INDEX `idx_airline` (`airline`),
  CONSTRAINT `fk_flight_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='航班预订详情';

-- 创建酒店预订详情表
CREATE TABLE IF NOT EXISTS `hotel_booking` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `booking_id` BIGINT NOT NULL,
  `hotel_name` VARCHAR(255) NOT NULL COMMENT '酒店名称',
  `hotel_address` VARCHAR(255) NOT NULL COMMENT '酒店地址',
  `room_type` VARCHAR(100) NOT NULL COMMENT '房型',
  `room_count` INT NOT NULL COMMENT '房间数量',
  `guest_info` JSON NOT NULL COMMENT '客人信息',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_booking_id` (`booking_id`),
  INDEX `idx_hotel_name` (`hotel_name`),
  CONSTRAINT `fk_hotel_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店预订详情';

-- 创建门票预订详情表
CREATE TABLE IF NOT EXISTS `ticket_booking` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `booking_id` BIGINT NOT NULL,
  `attraction_name` VARCHAR(255) NOT NULL COMMENT '景点名称',
  `ticket_type` VARCHAR(100) NOT NULL COMMENT '门票类型',
  `ticket_count` INT NOT NULL COMMENT '门票数量',
  `visit_date` DATE NOT NULL COMMENT '游玩日期',
  `visitor_info` JSON NOT NULL COMMENT '游客信息',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_booking_id` (`booking_id`),
  INDEX `idx_attraction_name` (`attraction_name`),
  INDEX `idx_visit_date` (`visit_date`),
  CONSTRAINT `fk_ticket_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门票预订详情';