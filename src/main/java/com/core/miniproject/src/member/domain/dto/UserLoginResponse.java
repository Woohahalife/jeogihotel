package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.common.security.jwt.AccessToken;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    private String secretKey;
    private String grantType;
    private Long expiredIn;

    public static UserLoginResponse toClient(AccessToken accessToken) {
        return UserLoginResponse.builder()
                .secretKey(accessToken.getSecretKey())
                .grantType(accessToken.getGrantType())
                .expiredIn(accessToken.getExpiredIn())
                .build();
    }
}
