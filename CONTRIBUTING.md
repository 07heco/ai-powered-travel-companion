# 贡献指南

感谢您对 AI-Powered Travel Companion 项目的关注！我们欢迎各种形式的贡献。

## 如何贡献

### 1. 报告问题
- 使用 GitHub Issues 报告 bug
- 提出功能建议
- 讨论代码改进

### 2. 提交代码
1. Fork 项目仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

### 3. 代码风格
- **前端**: 遵循 ESLint 和 Prettier 规则
- **后端**: 遵循 Java 代码规范
- **提交信息**: 使用清晰的描述，格式如 `type: subject`

### 4. 开发环境设置

#### 前置要求
- JDK 17+
- Node.js 18+
- Maven 3.8+
- Docker & Docker Compose

#### 本地开发
```bash
# 启动基础设施
docker-compose up -d

# 编译后端
cd backend
mvn clean install

# 启动前端
cd ../frontend
npm install
npm run dev
```

### 5. 测试
- 运行单元测试: `mvn test`
- 运行集成测试: `mvn verify`
- 确保所有测试通过后再提交 PR

### 6. 代码审查
所有 PR 都需要至少一位维护者审查才能合并。

## 许可证
通过贡献代码，您同意您的贡献将根据项目的 MIT 许可证发布。
