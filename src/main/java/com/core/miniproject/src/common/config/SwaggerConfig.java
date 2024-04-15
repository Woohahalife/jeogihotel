package com.core.miniproject.src.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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

        String root = "com.core.miniproject.src";
        String[] paths = {
                root + ".accommodation.controller",
                root + ".location.controller",
                root + ".member.controller",
                root + ".rate.controller",
                root + ".reservation.controller",
                root + ".room.controller",
                root + ".board.controller"
        };

        @Bean
        public GroupedOpenApi getEntireApi() {
                return GroupedOpenApi.builder()
                        .group("Entire")
                        .packagesToScan(paths)
                        .build();
        }

        /**
         * Swagger OpenAPI에 보안 요소 추가
         * 발급받은 JWT토큰을 인증에 활용해 API 테스트를 하기 위함
         */
        @Bean
        public OpenAPI apiKey() {
                SecurityScheme apiKey = new SecurityScheme() // API Key 정의
                        .type(SecurityScheme.Type.APIKEY) //API Key정의
                        .in(SecurityScheme.In.HEADER) // 헤더에 위치
                        .name("Authorization"); // 이름은 Authorization

                SecurityRequirement securityRequirement = new SecurityRequirement() // 보안 요구사항 정의
                        .addList("Bearer Token"); // Bearer Token 보안 요구사항 추가(모달창에 보임)

                return new OpenAPI()
                        .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                        .addSecurityItem(securityRequirement);
        }
}