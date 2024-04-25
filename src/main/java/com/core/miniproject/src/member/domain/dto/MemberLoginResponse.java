package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.common.security.jwt.AccessToken;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponse {

    private Long memberId;
    private Role role;
    private String secretKey;
    private String grantType;
    private Long expiredIn;

    public static MemberLoginResponse toClient(AccessToken accessToken, Long id, Role role) {
        return MemberLoginResponse.builder()
                .memberId(id)
                .role(role)
                .secretKey(accessToken.getSecretKey())
                .grantType(accessToken.getGrantType())
                .expiredIn(accessToken.getExpiredIn())
                .build();
    }
}
