package com.core.miniproject.src.common.security.principal;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.member.domain.entity.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberInfo {

    private Long id;
    private String email;
    private Role role;

    public static MemberInfo toParameter(Member member) {
        return MemberInfo.builder()
                .id(member.getId())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
