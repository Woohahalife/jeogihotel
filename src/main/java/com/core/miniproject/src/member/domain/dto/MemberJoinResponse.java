package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.member.domain.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinResponse {

    private String email;
    private String password;
    private String name;
    private Role role;

    public static MemberJoinResponse toClient(Member member) {
        return MemberJoinResponse.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
