-- 创建行程服务相关表结构

-- 1. 行程主表
drop table if exists trip;
create table trip (
    id bigint not null auto_increment comment '行程ID',
    user_id bigint not null comment '用户ID',
    trip_name varchar(100) not null comment '行程名称',
    destination varchar(100) comment '目的地',
    start_date datetime comment '开始日期',
    end_date datetime comment '结束日期',
    status varchar(20) default 'DRAFT' comment '状态：DRAFT-草稿, PLANNING-规划中, ONGOING-进行中, COMPLETED-已完成, CANCELLED-已取消',
    description text comment '行程描述',
    cover_image varchar(255) comment '封面图片',
    is_public tinyint default 0 comment '是否公开：0-私有, 1-公开',
    is_shared tinyint default 0 comment '是否分享：0-未分享, 1-已分享',
    collaborator_count int default 0 comment '协作人数',
    comment_count int default 0 comment '评论数',
    view_count int default 0 comment '浏览数',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key idx_user_id (user_id),
    key idx_status (status),
    key idx_start_date (start_date),
    key idx_end_date (end_date),
    key idx_deleted (deleted)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='行程主表';

-- 2. 行程详情表
drop table if exists trip_detail;
create table trip_detail (
    id bigint not null auto_increment comment '详情ID',
    trip_id bigint not null comment '行程ID',
    day int not null comment '第几天',
    title varchar(100) not null comment '当天标题',
    description text comment '当天描述',
    attractions varchar(500) comment '景点（JSON格式）',
    accommodation varchar(255) comment '住宿信息',
    transportation varchar(255) comment '交通信息',
    notes text comment '备注',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uk_trip_day (trip_id, day),
    key idx_trip_id (trip_id),
    key idx_day (day),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_trip_detail_trip_id foreign key (trip_id) references trip (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='行程详情表';

-- 3. 行程协作表
drop table if exists trip_collaborator;
create table trip_collaborator (
    id bigint not null auto_increment comment '协作ID',
    trip_id bigint not null comment '行程ID',
    user_id bigint not null comment '用户ID',
    role varchar(20) default 'VIEWER' comment '角色：OWNER-所有者, EDITOR-编辑者, VIEWER-查看者',
    status varchar(20) default 'PENDING' comment '状态：PENDING-待确认, ACCEPTED-已接受, REJECTED-已拒绝',
    joined_at datetime default current_timestamp comment '加入时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uk_trip_user (trip_id, user_id),
    key idx_trip_id (trip_id),
    key idx_user_id (user_id),
    key idx_role (role),
    key idx_status (status),
    constraint fk_trip_collaborator_trip_id foreign key (trip_id) references trip (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='行程协作表';

-- 4. 行程评论表
drop table if exists trip_comment;
create table trip_comment (
    id bigint not null auto_increment comment '评论ID',
    trip_id bigint not null comment '行程ID',
    user_id bigint not null comment '用户ID',
    content text not null comment '评论内容',
    parent_id bigint comment '父评论ID（回复）',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key idx_trip_id (trip_id),
    key idx_user_id (user_id),
    key idx_parent_id (parent_id),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_trip_comment_trip_id foreign key (trip_id) references trip (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='行程评论表';

-- 5. 行程附件表
drop table if exists trip_attachment;
create table trip_attachment (
    id bigint not null auto_increment comment '附件ID',
    trip_id bigint not null comment '行程ID',
    trip_detail_id bigint comment '行程详情ID',
    user_id bigint not null comment '上传用户ID',
    file_name varchar(255) not null comment '文件名',
    file_path varchar(255) not null comment '文件路径',
    file_size bigint comment '文件大小（字节）',
    file_type varchar(50) comment '文件类型',
    description varchar(255) comment '附件描述',
    is_public tinyint default 1 comment '是否公开：0-私有, 1-公开',
    status tinyint default 1 comment '状态：0-禁用，1-启用',
    deleted tinyint default 0 comment '逻辑删除：0-未删除，1-已删除',
    created_by bigint comment '创建人',
    updated_by bigint comment '更新人',
    created_at datetime default current_timestamp comment '创建时间',
    updated_at datetime default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key idx_trip_id (trip_id),
    key idx_trip_detail_id (trip_detail_id),
    key idx_user_id (user_id),
    key idx_file_type (file_type),
    key idx_status (status),
    key idx_deleted (deleted),
    constraint fk_trip_attachment_trip_id foreign key (trip_id) references trip (id) on delete cascade,
    constraint fk_trip_attachment_trip_detail_id foreign key (trip_detail_id) references trip_detail (id) on delete cascade
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='行程附件表';

-- 插入测试数据

-- 插入行程测试数据
insert into trip (user_id, trip_name, destination, start_date, end_date, status, description, cover_image, is_public) values
(1, '北京五日游', '北京', '2026-03-01', '2026-03-05', 'PLANNING', '北京经典景点五日游行程', 'https://example.com/beijing.jpg', 1),
(1, '上海迪士尼之旅', '上海', '2026-04-10', '2026-04-12', 'DRAFT', '上海迪士尼乐园主题游', 'https://example.com/shanghai.jpg', 0),
(2, '广州美食之旅', '广州', '2026-02-20', '2026-02-23', 'COMPLETED', '广州经典美食探索行程', 'https://example.com/guangzhou.jpg', 1);

-- 插入行程详情测试数据
insert into trip_detail (trip_id, day, title, description, attractions, accommodation, transportation, notes) values
(1, 1, '抵达北京', '抵达北京，入住酒店，自由活动', '{"attractions":["天安门广场","故宫"]}', '北京饭店', '飞机', '建议提前预订酒店'),
(1, 2, '北京经典一日', '游览北京经典景点', '{"attractions":["故宫","景山公园","北海公园"]}', '北京饭店', '地铁', '带好相机，准备走路'),
(1, 3, '长城一日游', '前往八达岭长城', '{"attractions":["八达岭长城","明十三陵"]}', '北京饭店', '包车', '早起，带足水和零食'),
(2, 1, '抵达上海', '抵达上海，入住酒店', '{"attractions":["外滩","南京路"]}', '上海外滩华尔道夫酒店', '高铁', '晚上可以逛外滩'),
(3, 1, '广州美食初体验', '品尝广州早餐和午餐', '{"attractions":["陶陶居","广州塔"]}', '广州四季酒店', '飞机', '一定要试试早茶');

-- 插入行程协作测试数据
insert into trip_collaborator (trip_id, user_id, role, status) values
(1, 2, 'EDITOR', 'ACCEPTED'),
(1, 3, 'VIEWER', 'PENDING'),
(3, 1, 'VIEWER', 'ACCEPTED');

-- 插入行程评论测试数据
insert into trip_comment (trip_id, user_id, content) values
(1, 2, '这个行程安排得很合理！'),
(1, 3, '长城一天会不会太累？'),
(3, 1, '广州美食真的很棒！');

-- 插入行程附件测试数据
insert into trip_attachment (trip_id, user_id, file_name, file_path, file_size, file_type, description) values
(1, 1, '北京行程规划.pdf', '/uploads/trip/1/beijing_plan.pdf', 1024000, 'application/pdf', '北京行程详细规划'),
(1, 2, '长城照片.jpg', '/uploads/trip/1/great_wall.jpg', 2048000, 'image/jpeg', '长城美景'),
(3, 2, '广州美食地图.jpg', '/uploads/trip/3/guangzhou_food.jpg', 1536000, 'image/jpeg', '广州美食推荐地图');
