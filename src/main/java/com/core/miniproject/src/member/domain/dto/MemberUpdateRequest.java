package com.core.miniproject.src.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {

    private String name;
    private String password;
    private String phoneNumber;
}
