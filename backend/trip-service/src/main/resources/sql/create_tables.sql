-- 创建行程表
CREATE TABLE IF NOT EXISTS `trip` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '行程ID',
  `trip_name` VARCHAR(255) NOT NULL COMMENT '行程名称',
  `trip_desc` TEXT COMMENT '行程描述',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `start_date` DATE COMMENT '开始日期',
  `end_date` DATE COMMENT '结束日期',
  `destination` VARCHAR(255) COMMENT '目的地',
  `theme` VARCHAR(100) COMMENT '行程主题',
  `budget` DECIMAL(10,2) COMMENT '预算',
  `is_public` TINYINT DEFAULT 0 COMMENT '是否公开(0:否,1:是)',
  `is_shared` TINYINT DEFAULT 0 COMMENT '是否分享(0:否,1:是)',
  `collaborator_count` INT DEFAULT 0 COMMENT '协作人数',
  `comment_count` INT DEFAULT 0 COMMENT '评论数',
  `view_count` INT DEFAULT 0 COMMENT '浏览次数',
  `status` TINYINT DEFAULT 1 COMMENT '状态(1:进行中,2:已完成,3:已取消)',
  `enabled` TINYINT DEFAULT 1 COMMENT '是否启用(0:禁用,1:启用)',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除(0:未删除,1:已删除)',
  `created_by` BIGINT COMMENT '创建人',
  `updated_by` BIGINT COMMENT '更新人',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_start_date` (`start_date`),
  INDEX `idx_end_date` (`end_date`),
  INDEX `idx_destination` (`destination`),
  INDEX `idx_is_public` (`is_public`),
  INDEX `idx_deleted` (`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行程表';

-- 创建行程详情表
CREATE TABLE IF NOT EXISTS `trip_detail` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '行程详情ID',
  `trip_id` BIGINT NOT NULL COMMENT '行程ID',
  `day` INT NOT NULL COMMENT '天数',
  `date` DATE COMMENT '日期',
  `title` VARCHAR(255) NOT NULL COMMENT '标题',
  `description` TEXT COMMENT '描述',
  `location` VARCHAR(255) COMMENT '地点',
  `address` VARCHAR(255) COMMENT '地址',
  `latitude` DOUBLE COMMENT '纬度',
  `longitude` DOUBLE COMMENT '经度',
  `activity_type` VARCHAR(100) COMMENT '活动类型',
  `cost` DECIMAL(10,2) COMMENT '费用',
  `start_time` TIME COMMENT '开始时间',
  `end_time` TIME COMMENT '结束时间',
  `status` TINYINT DEFAULT 1 COMMENT '状态(1:计划中,2:进行中,3:已完成)',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除(0:未删除,1:已删除)',
  `created_by` BIGINT COMMENT '创建人',
  `updated_by` BIGINT COMMENT '更新人',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_trip_id` (`trip_id`),
  INDEX `idx_day` (`day`),
  INDEX `idx_date` (`date`),
  INDEX `idx_location` (`location`),
  INDEX `idx_deleted` (`deleted`),
  CONSTRAINT `fk_trip_detail_trip_id` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行程详情表';

-- 创建行程协作表
CREATE TABLE IF NOT EXISTS `trip_collaborator` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '协作ID',
  `trip_id` BIGINT NOT NULL COMMENT '行程ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role` VARCHAR(50) DEFAULT 'member' COMMENT '角色(owner:所有者,admin:管理员,member:成员)',
  `permissions` VARCHAR(255) COMMENT '权限',
  `joined_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trip_user` (`trip_id`, `user_id`),
  INDEX `idx_trip_id` (`trip_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_role` (`role`),
  CONSTRAINT `fk_trip_collaborator_trip_id` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行程协作表';

-- 创建行程评论表
CREATE TABLE IF NOT EXISTS `trip_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `trip_id` BIGINT NOT NULL COMMENT '行程ID',
  `trip_detail_id` BIGINT COMMENT '行程详情ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID(0:顶级评论)',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `status` TINYINT DEFAULT 1 COMMENT '状态(1:正常,0:禁用)',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除(0:未删除,1:已删除)',
  `created_by` BIGINT COMMENT '创建人',
  `updated_by` BIGINT COMMENT '更新人',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_trip_id` (`trip_id`),
  INDEX `idx_trip_detail_id` (`trip_detail_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_parent_id` (`parent_id`),
  INDEX `idx_deleted` (`deleted`),
  CONSTRAINT `fk_trip_comment_trip_id` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_trip_comment_trip_detail_id` FOREIGN KEY (`trip_detail_id`) REFERENCES `trip_detail` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行程评论表';

-- 创建行程附件表
CREATE TABLE IF NOT EXISTS `trip_attachment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  `trip_id` BIGINT NOT NULL COMMENT '行程ID',
  `trip_detail_id` BIGINT COMMENT '行程详情ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `file_name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `file_path` VARCHAR(255) NOT NULL COMMENT '文件路径',
  `file_size` BIGINT COMMENT '文件大小',
  `file_type` VARCHAR(100) COMMENT '文件类型',
  `description` VARCHAR(255) COMMENT '描述',
  `is_public` TINYINT DEFAULT 1 COMMENT '是否公开(0:否,1:是)',
  `status` TINYINT DEFAULT 1 COMMENT '状态(1:正常,0:禁用)',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除(0:未删除,1:已删除)',
  `created_by` BIGINT COMMENT '创建人',
  `updated_by` BIGINT COMMENT '更新人',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_trip_id` (`trip_id`),
  INDEX `idx_trip_detail_id` (`trip_detail_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_deleted` (`deleted`),
  CONSTRAINT `fk_trip_attachment_trip_id` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_trip_attachment_trip_detail_id` FOREIGN KEY (`trip_detail_id`) REFERENCES `trip_detail` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='行程附件表';

-- 插入测试数据
INSERT INTO `trip` (`trip_name`, `trip_desc`, `user_id`, `start_date`, `end_date`, `destination`, `theme`, `budget`, `is_public`, `is_shared`, `collaborator_count`, `comment_count`, `view_count`, `status`, `enabled`, `deleted`, `created_by`, `updated_by`) VALUES
('日本东京5日游', '东京、浅草寺、富士山、迪士尼', 1, '2026-02-13', '2026-02-17', '日本东京', '休闲度假', 15000.00, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1),
('泰国普吉岛7日游', '普吉岛、皮皮岛、珊瑚岛', 2, '2026-02-20', '2026-02-26', '泰国普吉岛', '海岛度假', 20000.00, 0, 0, 0, 0, 0, 1, 1, 0, 2, 2),
('意大利罗马3日游', '罗马斗兽场、梵蒂冈、西班牙广场', 3, '2026-03-01', '2026-03-03', '意大利罗马', '文化古迹', 12000.00, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3);

-- 插入行程详情测试数据
INSERT INTO `trip_detail` (`trip_id`, `day`, `date`, `title`, `description`, `location`, `address`, `latitude`, `longitude`, `activity_type`, `cost`, `start_time`, `end_time`, `status`, `deleted`, `created_by`, `updated_by`) VALUES
(1, 1, '2026-02-13', '抵达东京', '抵达东京成田机场，入住酒店', '东京成田机场', '千叶县成田市', 35.772096, 140.392743, '交通', 5000.00, '14:00', '18:00', 1, 0, 1, 1),
(1, 2, '2026-02-14', '浅草寺', '参观浅草寺，购物', '浅草寺', '东京都台东区浅草2-3-1', 35.714716, 139.796767, '观光', 2000.00, '09:00', '15:00', 1, 0, 1, 1),
(1, 3, '2026-02-15', '富士山', '富士山一日游', '富士山', '静冈县富士宫市', 35.360622, 138.727495, '观光', 5000.00, '08:00', '18:00', 1, 0, 1, 1),
(1, 4, '2026-02-16', '迪士尼', '东京迪士尼乐园', '东京迪士尼乐园', '千叶县浦安市', 35.632897, 139.880394, '娱乐', 8000.00, '09:00', '20:00', 1, 0, 1, 1),
(1, 5, '2026-02-17', '返程', '返回国内', '东京成田机场', '千叶县成田市', 35.772096, 140.392743, '交通', 5000.00, '10:00', '14:00', 1, 0, 1, 1);

-- 插入行程协作测试数据
INSERT INTO `trip_collaborator` (`trip_id`, `user_id`, `role`, `permissions`) VALUES
(1, 2, 'member', 'view,edit'),
(1, 3, 'member', 'view'),
(2, 1, 'member', 'view,edit'),
(3, 1, 'member', 'view'),
(3, 2, 'member', 'view,edit');

-- 插入行程评论测试数据
INSERT INTO `trip_comment` (`trip_id`, `trip_detail_id`, `user_id`, `parent_id`, `content`, `status`, `deleted`, `created_by`, `updated_by`) VALUES
(1, 1, 2, 0, '这个行程看起来很棒！', 1, 0, 2, 2),
(1, 3, 3, 0, '富士山的景色一定很美', 1, 0, 3, 3),
(2, NULL, 1, 0, '普吉岛是我最喜欢的地方之一', 1, 0, 1, 1),
(3, NULL, 1, 0, '罗马的历史文化很丰富', 1, 0, 1, 1),
(3, NULL, 2, 0, '很期待这次旅行', 1, 0, 2, 2);

-- 插入行程附件测试数据
INSERT INTO `trip_attachment` (`trip_id`, `trip_detail_id`, `user_id`, `file_name`, `file_path`, `file_size`, `file_type`, `description`, `is_public`, `status`, `deleted`, `created_by`, `updated_by`) VALUES
(1, 3, 1, '富士山照片1.jpg', '/uploads/trip/1/202602/fuji1.jpg', 2048000, 'image/jpeg', '富士山远景', 1, 1, 0, 1, 1),
(1, 4, 1, '迪士尼照片1.jpg', '/uploads/trip/1/202602/disney1.jpg', 1536000, 'image/jpeg', '迪士尼城堡', 1, 1, 0, 1, 1),
(2, NULL, 2, '普吉岛照片1.jpg', '/uploads/trip/2/202602/phuket1.jpg', 2560000, 'image/jpeg', '普吉岛海滩', 1, 1, 0, 2, 2),
(3, NULL, 3, '罗马斗兽场照片1.jpg', '/uploads/trip/3/202603/colosseum1.jpg', 1843200, 'image/jpeg', '罗马斗兽场', 1, 1, 0, 3, 3);