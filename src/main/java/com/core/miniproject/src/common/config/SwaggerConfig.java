package com.core.miniproject.src.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "숙박예약 시스템 api 명세서",
                description = "Java, Spring 기반 숙박예약 api 구현 프로젝트",
                version = "v1",
                termsOfService = "https://www.notion.so/2-7b8b01ea77a04b278b418b0b8dfbcbfd"
        )
)
@RequiredArgsConstructor
public class SwaggerConfig {

        String[] paths = {"/user"}; // 기능별 root url 구분

        @Bean
        public GroupedOpenApi getUserApi() { // root url에 따라 명세 구분
                return GroupedOpenApi.builder()
                        .group("user")
                        .pathsToMatch(paths[0] + "/**")
                        .build();
        }
}