package com.gocile.shikesystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ShikeSystem API")
                        .version("1.0.0")
                        .description("拾课选课系统的api文件"))
                .externalDocs(new ExternalDocumentation()
                        .description("详细api文档")
                        .url("https://shikesystemapi.com"));
    }
}