package com.core.miniproject.src.common.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, RefreshToken> redisTemplate;
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:";
    private static final String MEMBER_ID_KEY_PREFIX = "memberId:";

    public void saveRefreshToken(RefreshToken refreshToken) {
        String key = REFRESH_TOKEN_KEY_PREFIX + refreshToken.getAccessToken();
        long newTtl = TimeUnit.DAYS.toSeconds(3);
        redisTemplate.opsForValue().set(key, refreshToken, newTtl, TimeUnit.SECONDS);
    }

    public RefreshToken getRefreshTokenByAccessToken(String accessToken) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY_PREFIX + accessToken);
    }

//    public RefreshToken getRefreshTokenByMemberId(String memberId) {
//        return redisTemplate.opsForValue().get(MEMBER_ID_KEY_PREFIX + memberId);
//    }

    public void updateRefreshToken(RefreshToken refreshToken, String oldAccessToken) {
        String oldKey = REFRESH_TOKEN_KEY_PREFIX + oldAccessToken;
        String newKey = REFRESH_TOKEN_KEY_PREFIX + refreshToken.getAccessToken();

        Long ttl = redisTemplate.getExpire(oldKey);
        long newTtl = TimeUnit.SECONDS.toSeconds(ttl);

        redisTemplate.delete(oldKey);

        redisTemplate.opsForValue().set(newKey, refreshToken, newTtl, TimeUnit.SECONDS);
    }

    public void deleteRefreshToken(RefreshToken refreshToken) {
        redisTemplate.delete(refreshToken.getId());
    }
}
