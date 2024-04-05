package com.core.miniproject.src.member.domain.dto;

import com.core.miniproject.src.member.domain.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateResponse {

    private Long memberId;
    private String name;
    private String phoneNumber;

    public static MemberUpdateResponse toClient(Member member) {
        return MemberUpdateResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
