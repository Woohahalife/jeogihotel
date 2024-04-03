package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.member.domain.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public static MemberInfoResponse toClient(Member member) {
        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
