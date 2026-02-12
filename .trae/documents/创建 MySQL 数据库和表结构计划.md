# 创建 MySQL 数据库和表结构计划

## 1. 任务概述

根据 user-service 中的登录认证功能和参考文档，在 MySQL 中创建 `travel_companion` 数据库，并创建对应的用户表和第三方账号表。

## 2. 技术参考

- **实体类**：`User.java` 和 `UserThirdParty.java`
- **参考文档**：`plan_20260211_121431.md`
- **数据库**：MySQL 8.0

## 3. 执行步骤

### 步骤 1：连接 MySQL 数据库

使用 MySQL 客户端连接到本地或远程 MySQL 服务器。

### 步骤 2：创建 travel_companion 数据库

执行 SQL 语句创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS travel_companion DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 步骤 3：创建 user 表

根据 `User.java` 实体类和参考文档，创建用户表：

- 字段包括：id、phone、password、nickname、avatar、gender、birthday、email、city、bio、type、status、create_time、update_time、deleted
- 主键为 id，自增
- 适当的索引和约束

### 步骤 4：创建 user_third_party 表

根据 `UserThirdParty.java` 实体类和参考文档，创建第三方账号关联表：

- 字段包括：id、user_id、third_type、third_openid、third_unionid、create_time、update_time
- 主键为 id，自增
- 外键关联 user 表的 id 字段
- 适当的索引和约束

### 步骤 5：验证表结构

执行 `SHOW CREATE TABLE` 语句验证表结构是否正确，确保所有字段和约束都符合设计要求。

## 4. 预期结果

- 成功创建 `travel_companion` 数据库
- 成功创建 `user` 表和 `user_third_party` 表
- 表结构与实体类和参考文档一致
- 数据库可以正常使用，支持用户认证功能

## 5. 注意事项

- 确保 MySQL 服务正在运行
- 确保有足够的权限创建数据库和表
- 使用正确的字符集和排序规则
- 确保外键关联正确设置
- 为频繁查询的字段添加适当的索引