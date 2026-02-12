-- 创建目的地服务相关表结构

-- 1. 目的地表
drop table if exists destination;
create table destination (
    id bigint not null auto_increment comment '目的地ID',
    name varchar(100) not null comment '目的地名称',
    country varchar(50) not null comment '国家',
    city varchar(50) not null comment '城市',
    description text comment '目的地描述',
    cover_image varchar(255) comment '封面图片',
    latitude double comment '纬度',
    longitude double comment '经度',
    popular_score int default 0 comment '热门评分',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uk_name_city (name, city),
    key idx_country (country),
    key idx_city (city),
    key idx_status (status),
    key idx_deleted (deleted),
    key idx_popular_score (popular_score)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='目的地表';

-- 2. 目的地图片表
drop table if exists destination_image;
create table destination_image (
    id bigint not null auto_increment comment '图片ID',
    destination_id bigint not null comment '目的地ID',
    image_url varchar(255) not null comment '图片URL',
    sort int default 0 comment '排序',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key idx_destination_id (destination_id),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_destination_image_destination_id foreign key (destination_id) references destination (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='目的地图片表';

-- 3. 目的地标签表
drop table if exists destination_tag;
create table destination_tag (
    id bigint not null auto_increment comment '标签ID',
    destination_id bigint not null comment '目的地ID',
    tag_name varchar(50) not null comment '标签名称',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uk_destination_tag (destination_id, tag_name),
    key idx_destination_id (destination_id),
    key idx_tag_name (tag_name),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_destination_tag_destination_id foreign key (destination_id) references destination (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='目的地标签表';

-- 4. 景点表
drop table if exists attraction;
create table attraction (
    id bigint not null auto_increment comment '景点ID',
    destination_id bigint not null comment '目的地ID',
    name varchar(100) not null comment '景点名称',
    description text comment '景点描述',
    address varchar(255) comment '景点地址',
    image_url varchar(255) comment '景点图片',
    latitude double comment '纬度',
    longitude double comment '经度',
    rating double default 0 comment '评分',
    category varchar(50) comment '景点分类',
    opening_hours varchar(100) comment '开放时间',
    ticket_price double comment '门票价格',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key idx_destination_id (destination_id),
    key idx_category (category),
    key idx_rating (rating),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_attraction_destination_id foreign key (destination_id) references destination (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='景点表';

-- 5. 景点评价表
drop table if exists attraction_review;
create table attraction_review (
    id bigint not null auto_increment comment '评价ID',
    attraction_id bigint not null comment '景点ID',
    user_id bigint not null comment '用户ID',
    rating int not null comment '评分：1-5星',
    content text comment '评价内容',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uk_attraction_user (attraction_id, user_id),
    key idx_attraction_id (attraction_id),
    key idx_user_id (user_id),
    key idx_rating (rating),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_attraction_review_attraction_id foreign key (attraction_id) references attraction (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='景点评价表';

-- 6. 景点图片表
drop table if exists attraction_image;
create table attraction_image (
    id bigint not null auto_increment comment '图片ID',
    attraction_id bigint not null comment '景点ID',
    image_url varchar(255) not null comment '图片URL',
    sort int default 0 comment '排序',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key idx_attraction_id (attraction_id),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_attraction_image_attraction_id foreign key (attraction_id) references attraction (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='景点图片表';

-- 7. 景点标签表
drop table if exists attraction_tag;
create table attraction_tag (
    id bigint not null auto_increment comment '标签ID',
    attraction_id bigint not null comment '景点ID',
    tag_name varchar(50) not null comment '标签名称',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uk_attraction_tag (attraction_id, tag_name),
    key idx_attraction_id (attraction_id),
    key idx_tag_name (tag_name),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_attraction_tag_attraction_id foreign key (attraction_id) references attraction (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='景点标签表';

-- 8. 目的地推荐表
drop table if exists destination_recommendation;
create table destination_recommendation (
    id bigint not null auto_increment comment '推荐ID',
    user_id bigint not null comment '用户ID',
    destination_id bigint not null comment '目的地ID',
    score double default 0 comment '推荐分数',
    reason varchar(255) comment '推荐原因',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uk_user_destination (user_id, destination_id),
    key idx_user_id (user_id),
    key idx_destination_id (destination_id),
    key idx_score (score),
    key idx_deleted (deleted),
    constraint fk_recommendation_destination_id foreign key (destination_id) references destination (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='目的地推荐表';

-- 插入测试数据

-- 插入目的地测试数据
insert into destination (name, country, city, description, cover_image, latitude, longitude, popular_score) values
('北京', '中国', '北京', '中国首都，历史悠久的文化名城', 'https://example.com/beijing.jpg', 39.9042, 116.4074, 95),
('上海', '中国', '上海', '国际化大都市，现代与传统交融', 'https://example.com/shanghai.jpg', 31.2304, 121.4737, 90),
('广州', '中国', '广州', '华南地区经济中心，美食之都', 'https://example.com/guangzhou.jpg', 23.1291, 113.2644, 85),
('深圳', '中国', '深圳', '改革开放前沿，创新之城', 'https://example.com/shenzhen.jpg', 22.5431, 114.0579, 88),
('杭州', '中国', '杭州', '人间天堂，西湖风光秀丽', 'https://example.com/hangzhou.jpg', 30.2741, 120.1551, 86);

-- 插入景点测试数据
insert into attraction (destination_id, name, description, address, image_url, latitude, longitude, rating, category, opening_hours, ticket_price) values
(1, '故宫', '中国明清两代的皇家宫殿', '北京市东城区景山前街4号', 'https://example.com/forbidden_city.jpg', 39.9163, 116.3972, 4.8, '文化古迹', '8:30-17:00', 60),
(1, '长城', '中国古代伟大的防御工程', '北京市怀柔区', 'https://example.com/great_wall.jpg', 40.4319, 116.5704, 4.9, '文化古迹', '7:30-18:00', 40),
(2, '外滩', '上海标志性建筑群', '上海市黄浦区中山东一路', 'https://example.com/bund.jpg', 31.2404, 121.4919, 4.7, '现代建筑', '全天开放', 0),
(2, '东方明珠', '上海地标性建筑', '上海市浦东新区世纪大道1号', 'https://example.com/oriental_pearl.jpg', 31.2397, 121.4999, 4.6, '现代建筑', '8:00-22:00', 120),
(3, '广州塔', '广州新地标', '广州市海珠区阅江西路222号', 'https://example.com/canton_tower.jpg', 23.1062, 113.3249, 4.5, '现代建筑', '9:00-23:00', 150);

-- 插入目的地图片测试数据
insert into destination_image (destination_id, image_url, sort) values
(1, 'https://example.com/beijing_1.jpg', 1),
(1, 'https://example.com/beijing_2.jpg', 2),
(1, 'https://example.com/beijing_3.jpg', 3),
(2, 'https://example.com/shanghai_1.jpg', 1),
(2, 'https://example.com/shanghai_2.jpg', 2);

-- 插入目的地标签测试数据
insert into destination_tag (destination_id, tag_name) values
(1, '历史文化'),
(1, '古都'),
(1, '美食'),
(2, '现代都市'),
(2, '购物'),
(2, '美食'),
(3, '美食'),
(3, '商业中心'),
(4, '创新科技'),
(4, '现代都市'),
(5, '自然风光'),
(5, '文化古迹');

-- 插入景点评价测试数据
insert into attraction_review (attraction_id, user_id, rating, content) values
(1, 1, 5, '非常震撼，历史感浓厚'),
(1, 2, 4, '人很多，建议早点去'),
(2, 1, 5, '不到长城非好汉，值得一去'),
(3, 3, 4, '夜景很美，适合拍照'),
(4, 2, 5, '视野开阔，可以看到整个上海');
