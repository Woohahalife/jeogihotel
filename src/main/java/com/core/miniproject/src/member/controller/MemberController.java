package com.core.miniproject.src.member.controller;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.jwt.AccessToken;
import com.core.miniproject.src.member.domain.dto.MemberLoginResponse;
import com.core.miniproject.src.member.domain.dto.NewTokenResponse;
import com.core.miniproject.src.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.core.miniproject.src.common.response.BaseResponse.response;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "회원 수정 & 삭제 api", description = "회원 관련 api - 보안 접근 필요")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/v1/member/token")
    public BaseResponse<NewTokenResponse> newToken(@RequestHeader(value = "Authorization") String authorization) {
        log.info("[Post Mapping - 토큰 재발급입니다.");

        AccessToken accessToken = memberService.newToken(authorization);

        return response(NewTokenResponse.toClient(accessToken));
    }
}
