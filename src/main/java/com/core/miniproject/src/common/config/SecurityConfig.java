package com.core.miniproject.src.common.config;

import com.core.miniproject.src.common.exception.JwtAuthenticationEntryPoint;
import com.core.miniproject.src.common.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .httpBasic(AbstractHttpConfigurer -> AbstractHttpConfigurer.disable())
                .cors(AbstractHttpConfigurer -> AbstractHttpConfigurer.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/public-api/**", "/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                );

        http
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling
                        (handler -> handler.authenticationEntryPoint(entryPoint));

        return http.build();
    }
    // H2Console에 대한 필터를 차단(spring.h2.console.enabled = true 일 때만 작동)
    @Bean
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web
                .ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers("/swagger", "/favicon");
    }
}
