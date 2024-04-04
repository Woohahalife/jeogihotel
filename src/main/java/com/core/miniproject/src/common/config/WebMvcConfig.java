package com.core.miniproject.src.common.config;

import com.core.miniproject.src.common.security.AuthMemberResolver;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final MemberRepository memberRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") // cors를 적용할 spring서버의 url 패턴.
                .allowedOrigins("http://localhost:5173, http://15.164.19.60") // cors를 허용할 도메인. 제한을 모두 해제하려면 "**"
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // cors를 허용할 method
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthMemberResolver(memberRepository));
    }
}
