package com.core.miniproject.src.member.service;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.jwt.AccessToken;
import com.core.miniproject.src.common.security.jwt.JwtTokenGenerator;
import com.core.miniproject.src.member.domain.dto.MemberJoinRequest;
import com.core.miniproject.src.member.domain.dto.MemberJoinResponse;
import com.core.miniproject.src.member.domain.dto.MemberLoginRequest;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;

    public MemberJoinResponse join(MemberJoinRequest request) {
        /*
        회원 정보(email, password, name, email, phoneNumber)를 등록한다.
            -  email이 이미 존재할 경우 에러 반환
            -  중복 이메일 허용 x
         */
        memberRepository.findByMemberEmail(request.getEmail())
                .ifPresent(member -> {
                    throw new BaseException(DUPLICAE_EMAIL);
                });


        Member saveMember = memberRepository.save(insertMemberDataFromRequest(request));

        log.info("MemberEntity has created for join with ID: {} email: {} name : {}",
                saveMember.getId(), saveMember.getEmail(), saveMember.getName());

        return MemberJoinResponse.toClient(saveMember);

    }

    private Member insertMemberDataFromRequest(MemberJoinRequest request) {
        return Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.USER)
                .build();
    }

    public AccessToken login(MemberLoginRequest request) {

        /*
        로그인 기능
            - email 등록되어 있지 않다면 에러 반환
            - password가 일치하지 않는다면 에러 반환
         */

        Member member = memberRepository.findByMemberEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        validatePassword(request, member);

        return jwtTokenGenerator.generateAccessToken(member.getEmail(), member.getRole());
    }

    private void validatePassword(MemberLoginRequest request, Member member) {
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BaseException(INVALID_PASSWORD);
        }
    }
}
