package com.core.miniproject.src.common.security.jwt;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.principal.MemberPrincipalService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String PUBLIC_API_PREFIX = "/public-api";

    private final JwtTokenGenerator jwtTokenGenerator;
    private final MemberPrincipalService memberPrincipalService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (checkPublicApi(request, response, filterChain)) { // /public-api : 필터 무시
//            System.out.println(request.getRequestURI() + " 필터 작동 X 확인");
            return;
        }

        try {
            String accessToken = parseBearerToken(request); // 순수 토큰값 추출
            UserDetails userDetails = parseUserEmail(accessToken); // Member정보를 UserDetails 객체로 변환
            configureAuthenticatedUser(userDetails);

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_ERROR, "필터 통과 X");
        }

        filterChain.doFilter(request, response);
    }

    private void configureAuthenticatedUser(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String parseBearerToken(HttpServletRequest request) {

        String authorization = extractAuthorization(request);
        log.info("[JwtTokenFilter] Extract authorization for Jwt token: {}", authorization);
        return authorization.split(" ")[1]; // Bearer 빼고 토큰값만 추출

    }

    private String extractAuthorization(HttpServletRequest request) {
        // request에서 Authorization 헤더 추출
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION); // 실제 값 체크 필요

        if (authorization == null || !authorization.startsWith(BEARER)) {
            throw new BaseException(BaseResponseStatus.NOT_BEARER_TOKEN);
        }
        return authorization;
    }

    // accessToken으로부터 회원 정보 추출해 UserDetails로 넘김
    private UserDetails parseUserEmail(String accessToken) {

        String userEmail = jwtTokenGenerator.getUserEmail(accessToken);
        return memberPrincipalService.loadUserByUsername(userEmail);
    }

    private boolean checkPublicApi(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws IOException, ServletException {

        if (request.getRequestURI().startsWith(PUBLIC_API_PREFIX) ||
            request.getRequestURI().contains("h2-console") ||
            request.getRequestURI().contains("swagger") ||
            request.getRequestURI().contains("favicon")
        ) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }
}
