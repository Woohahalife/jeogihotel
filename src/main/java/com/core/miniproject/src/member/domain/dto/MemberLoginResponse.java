package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.common.security.jwt.AccessToken;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponse {

    private String secretKey;
    private String grantType;
    private Long expiredIn;

    public static MemberLoginResponse toClient(AccessToken accessToken) {
        return MemberLoginResponse.builder()
                .secretKey(accessToken.getSecretKey())
                .grantType(accessToken.getGrantType())
                .expiredIn(accessToken.getExpiredIn())
                .build();
    }
}
