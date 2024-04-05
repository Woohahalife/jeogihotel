package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.member.domain.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {

    private String email;
    private String name;
    private String phoneNumber;
    private Role role;

    public static MemberInfoResponse toClient(Member member) {
        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }
}
