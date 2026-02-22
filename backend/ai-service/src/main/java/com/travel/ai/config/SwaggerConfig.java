package com.travel.ai.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "AI服务接口文档",
                description = "提供AI旅行规划、AR实景导览、智能预算和旅伴匹配等功能的API接口文档",
                version = "1.0.0"
        ),
        servers = @Server(url = "http://localhost:8085/ai", description = "AI服务本地开发环境")
)
public class SwaggerConfig {
}