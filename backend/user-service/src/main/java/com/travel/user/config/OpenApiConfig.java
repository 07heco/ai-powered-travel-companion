package com.travel.user.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Knife4j 基础配置
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI-Powered Travel Companion - User Service API")
                        .description("用户服务接口文档，包含登录、注册、验证码、用户信息维护等接口")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Travel Companion Team"))
                        .license(new License()
                                .name("Internal Use")))
                .externalDocs(new ExternalDocumentation()
                        .description("User Service Module"));
    }

    @Bean
    public GroupedOpenApi userAuthApi() {
        return GroupedOpenApi.builder()
                .group("user-auth")
                .packagesToScan("com.travel.user.controller")
                .pathsToMatch("/api/auth/**")
                .build();
    }
}
