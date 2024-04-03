package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.member.domain.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuthResponse {

    private String email;
    private String name;
    private Role role;

    public static MemberAuthResponse toClient(Member member) {
        return MemberAuthResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
