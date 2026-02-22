-- 创建AI服务相关数据表

-- AI旅行计划表
CREATE TABLE IF NOT EXISTS `ai_plan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `plan_name` VARCHAR(255) NOT NULL COMMENT '计划名称',
  `destination` VARCHAR(255) NOT NULL COMMENT '目的地',
  `start_date` DATETIME NOT NULL COMMENT '开始日期',
  `end_date` DATETIME NOT NULL COMMENT '结束日期',
  `plan_content` TEXT COMMENT '计划内容',
  `status` VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI旅行计划表';

-- 旅游伙伴表
CREATE TABLE IF NOT EXISTS `travel_mate` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `name` VARCHAR(255) NOT NULL COMMENT '伙伴名称',
  `avatar` VARCHAR(255) COMMENT '头像',
  `personality` TEXT COMMENT '个性描述',
  `expertise` TEXT COMMENT '专业领域',
  `status` VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='旅游伙伴表';

-- AR景点表
CREATE TABLE IF NOT EXISTS `ar_landmark` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(255) NOT NULL COMMENT '景点名称',
  `english_name` VARCHAR(255) COMMENT '英文名称',
  `category` VARCHAR(255) COMMENT '分类',
  `rating` DOUBLE COMMENT '评分',
  `description` TEXT COMMENT '描述',
  `history` TEXT COMMENT '历史',
  `open_time` VARCHAR(255) COMMENT '开放时间',
  `ticket_price` VARCHAR(255) COMMENT '票价',
  `highlights` TEXT COMMENT '亮点',
  `tips` TEXT COMMENT '提示',
  `location` VARCHAR(255) COMMENT '位置',
  `latitude` DOUBLE COMMENT '纬度',
  `longitude` DOUBLE COMMENT '经度',
  `image_url` VARCHAR(255) COMMENT '图片URL',
  `recognition_keywords` TEXT COMMENT '识别关键词',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_name` (`name`),
  INDEX `idx_category` (`category`),
  INDEX `idx_location` (`location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AR景点表';

-- 预算表
CREATE TABLE IF NOT EXISTS `budget` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `trip_name` VARCHAR(255) NOT NULL COMMENT '旅行名称',
  `total_budget` DOUBLE NOT NULL COMMENT '总预算',
  `spent_amount` DOUBLE DEFAULT 0 COMMENT '已花费金额',
  `start_date` VARCHAR(50) COMMENT '开始日期',
  `end_date` VARCHAR(50) COMMENT '结束日期',
  `destination` VARCHAR(255) COMMENT '目的地',
  `status` VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预算表';

-- 支出记录表
CREATE TABLE IF NOT EXISTS `expense` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `budget_id` BIGINT NOT NULL COMMENT '预算ID',
  `category` VARCHAR(255) NOT NULL COMMENT '分类',
  `amount` DOUBLE NOT NULL COMMENT '金额',
  `description` TEXT COMMENT '描述',
  `date` VARCHAR(50) NOT NULL COMMENT '日期',
  `payment_method` VARCHAR(255) COMMENT '支付方式',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_budget_id` (`budget_id`),
  INDEX `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支出记录表';

-- 招募帖表
CREATE TABLE IF NOT EXISTS `recruit_post` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `author_name` VARCHAR(255) NOT NULL COMMENT '作者名称',
  `destination` VARCHAR(255) NOT NULL COMMENT '目的地',
  `travel_date` VARCHAR(255) NOT NULL COMMENT '旅行日期',
  `duration` VARCHAR(255) COMMENT '旅行时长',
  `current_members` INT DEFAULT 1 COMMENT '当前人数',
  `max_members` INT NOT NULL COMMENT '最大人数',
  `tags` TEXT COMMENT '标签',
  `budget` VARCHAR(255) COMMENT '预算',
  `description` TEXT COMMENT '描述',
  `status` VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_destination` (`destination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='招募帖表';

-- 旅伴匹配表
CREATE TABLE IF NOT EXISTS `travel_mate_match` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `matched_user_id` BIGINT NOT NULL COMMENT '匹配用户ID',
  `destination` VARCHAR(255) NOT NULL COMMENT '目的地',
  `travel_date` VARCHAR(255) NOT NULL COMMENT '旅行日期',
  `travel_style` VARCHAR(255) COMMENT '旅行风格',
  `interests` TEXT COMMENT '兴趣爱好',
  `match_score` INT COMMENT '匹配分数',
  `status` VARCHAR(50) DEFAULT 'PENDING' COMMENT '状态',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_matched_user_id` (`matched_user_id`),
  INDEX `idx_match_score` (`match_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='旅伴匹配表';

-- 插入测试数据

-- AR景点测试数据
INSERT INTO `ar_landmark` (`name`, `english_name`, `category`, `rating`, `description`, `history`, `open_time`, `ticket_price`, `highlights`, `tips`, `location`, `latitude`, `longitude`, `image_url`, `recognition_keywords`) VALUES
('故宫博物院', 'The Palace Museum', '世界文化遗产', 4.9, '故宫博物院建立于1925年，是在明清两代皇宫紫禁城的基础上建立起来的，是中国最大的古代文化艺术博物馆。', '始建于1406年，距今已有600余年历史', '08:30-17:00', '60元', '太和殿,养心殿,珍宝馆,钟表馆', '建议预留3-4小时游览时间,周一闭馆,需提前预约', '北京市东城区景山前街4号', 39.916345, 116.397155, 'https://example.com/forbidden-city.jpg', '故宫,紫禁城,明清皇宫,北京'),
('长城', 'The Great Wall', '世界文化遗产', 4.8, '长城是中国古代的伟大防御工程，是世界上最伟大的建筑之一。', '始建于春秋战国时期，距今已有2000余年历史', '08:00-17:30', '40元', '八达岭,慕田峪,司马台,嘉峪关', '建议穿舒适的鞋子,带足够的水和食物', '北京市延庆区八达岭长城景区', 40.35983, 116.01776, 'https://example.com/great-wall.jpg', '长城,八达岭,中国古代防御工程'),
('西湖', 'West Lake', '世界文化遗产', 4.7, '西湖是中国浙江省杭州市西湖区龙井路1号的一个著名旅游景点，是中国大陆首批国家重点风景名胜区和中国十大风景名胜之一。', '已有2000余年历史，是中国古典园林代表作', '全天开放', '免费', '断桥,雷峰塔,苏堤,白堤', '建议游览3-5小时,可乘游船游览', '浙江省杭州市西湖区', 30.24276, 120.14849, 'https://example.com/west-lake.jpg', '西湖,杭州,断桥,雷峰塔');

-- 招募帖测试数据
INSERT INTO `recruit_post` (`user_id`, `author_name`, `destination`, `travel_date`, `duration`, `current_members`, `max_members`, `tags`, `budget`, `description`, `status`) VALUES
(1, '小明', '北京', '2024-03-15至2024-03-20', '5天', 1, 3, '文化,历史,美食', '1000-1500元/天', '计划游览故宫、长城、颐和园等著名景点，希望找志同道合的旅伴一起探索北京的文化底蕴。', 'ACTIVE'),
(2, '小红', '上海', '2024-03-20至2024-03-25', '5天', 1, 4, '现代,购物,美食', '1500-2000元/天', '计划游览外滩、迪士尼、南京路等景点，希望找喜欢拍照和购物的旅伴。', 'ACTIVE'),
(3, '小李', '成都', '2024-03-10至2024-03-15', '5天', 2, 4, '美食,休闲,文化', '800-1200元/天', '计划游览宽窄巷子、锦里、熊猫基地等景点，重点是品尝成都美食，希望找喜欢美食的旅伴。', 'ACTIVE');
