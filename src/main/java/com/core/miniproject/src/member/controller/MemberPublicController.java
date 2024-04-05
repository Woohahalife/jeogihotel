package com.core.miniproject.src.member.controller;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.jwt.AccessToken;
import com.core.miniproject.src.member.domain.dto.MemberJoinRequest;
import com.core.miniproject.src.member.domain.dto.MemberJoinResponse;
import com.core.miniproject.src.member.domain.dto.MemberLoginRequest;
import com.core.miniproject.src.member.domain.dto.MemberLoginResponse;
import com.core.miniproject.src.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.core.miniproject.src.common.response.BaseResponse.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public-api")
@Tag(name = "회원 로그인 & 회원가입 api", description = "회원 관련 api - 보안 설정 X")
public class MemberPublicController {

    private final MemberService memberService;

    @PostMapping("/v1/member/join")
    public BaseResponse<MemberJoinResponse> join(@RequestBody MemberJoinRequest request) {
        log.info("Post Mapping - create a new Member Request Details: email: {}",
                request.getEmail());
        MemberJoinResponse response = memberService.join(request);

        return response(response);
    }

    @PostMapping("/v1/member/login")
    public BaseResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        log.info("[Post Mapping - User is attempting to login with username: {}", request.getEmail());
        MemberLoginResponse login = memberService.login(request);

        return response(login);
    }

}
