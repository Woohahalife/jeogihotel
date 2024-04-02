package com.core.miniproject.src.common.security.jwt;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenGenerator {

    private static final String BEARER = "Bearer";
    private SecretKey key;

    @Value("${jwt.access-token.access-expired}")
    private Long accessExpiredTimeMills;

    public JwtTokenGenerator(@Value("${jwt.access-token.secret-key}") String secret) {
        this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public AccessToken generateAccessToken(String email, Role role) {
        return AccessToken.builder()
                .secretKey(createJwtToken(email, role))
                .grantType(BEARER)
                .expiredIn(accessExpiredTimeMills)
                .build();
    }

    public String createJwtToken(String email, Role role) { // 토큰 생성 메서드

        return Jwts.builder()
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiredTimeMills))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(String email) {

        return Jwts.builder()
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 3*24*60*60*1000))
                .signWith(key)
                .compact();
    }

    public String getUserEmail(String token) {

        Claims claims = extractClaim(token, key);

        return claims.get("email", String.class);
    }

    public boolean isExpired(String token) {
        try {
            Date expiredDate = extractClaim(token, key).getExpiration();

            // 만료 시간이 현재 시간 이전이면 토큰이 만료됨
            return expiredDate.before(new Date());

        } catch (ExpiredJwtException e) {
            throw new BaseException(BaseResponseStatus.EXPIRED_ACCESSTOKEN);
        }

    }

    private Claims extractClaim(String token, SecretKey key) {

        return Jwts.parser()
                .verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
