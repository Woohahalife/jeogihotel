package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.common.security.jwt.AccessToken;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewTokenResponse {

    private String secretKey;
    private String grantType;
    private Long expiredIn;

    public static NewTokenResponse toClient(AccessToken accessToken) {
        return NewTokenResponse.builder()
                .secretKey(accessToken.getSecretKey())
                .grantType(accessToken.getGrantType())
                .expiredIn(accessToken.getExpiredIn())
                .build();
    }
}
