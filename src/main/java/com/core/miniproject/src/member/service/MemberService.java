package com.core.miniproject.src.member.service;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.jwt.AccessToken;
import com.core.miniproject.src.common.security.jwt.JwtTokenGenerator;
import com.core.miniproject.src.common.security.jwt.RefreshToken;
import com.core.miniproject.src.common.security.jwt.RefreshTokenService;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.member.domain.dto.*;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Transactional
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

    @Transactional
    public MemberLoginResponse login(MemberLoginRequest request) {
        /*
        로그인 기능
            - email 등록되어 있지 않다면 에러 반환
            - password가 일치하지 않는다면 에러 반환
         */
        Member member = memberRepository.findByMemberEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        String requestPassword = request.getPassword();
        validatePassword(requestPassword, member);

        AccessToken token = jwtTokenGenerator.generateAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtTokenGenerator.createRefreshToken(member.getEmail());

        refreshTokenService.saveRefreshToken(new RefreshToken(String.valueOf(member.getId()), refreshToken, token.getSecretKey()));

        return MemberLoginResponse.toClient(token, member.getId(), member.getRole());
    }

    private void validatePassword(String requestPassword, Member member) {
        if (!passwordEncoder.matches(requestPassword, member.getPassword())) {
            throw new BaseException(INVALID_PASSWORD);
        }
    }

    public AccessToken newToken(String authorization) {

        String accessToken = authorization.split(" ")[1];

        RefreshToken refreshToken = refreshTokenService.getRefreshTokenByAccessToken(accessToken);

        Member member = memberRepository.findById(Long.valueOf(refreshToken.getId())).orElseThrow();

        AccessToken token = jwtTokenGenerator.generateAccessToken(member.getEmail(), member.getRole());

        refreshTokenService.updateRefreshToken(new RefreshToken(
                String.valueOf(refreshToken.getId()), refreshToken.getRefreshToken(), token.getSecretKey()), refreshToken.getAccessToken());

        return token;
    }

    public MemberInfoResponse getMemberInfo(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        return MemberInfoResponse.toClient(member);
    }

    @Transactional
    public MemberAuthResponse AuthorizationAdmin(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        member.grantRoleToAdmin(Role.ADMIN);

        return MemberAuthResponse.toClient(member);
    }

    @Transactional
    public MemberUpdateResponse updateMemberInfo(Long memberId, MemberUpdateRequest request, MemberInfo memberInfo) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND)); // 회원 정보 검증

        String requestedPassword = request.getPassword();

        requestedPassword = selectEncodePasswordValidate(requestedPassword, member);

        member.updateMember(request, requestedPassword); // 회원 정보 수정 ( 수정할 내용이 없다면 받은 내용 그대로 입력 )

        return MemberUpdateResponse.toClient(member);
    }

    private String selectEncodePasswordValidate(String requestedPassword, Member member) {
        if (requestedPassword == null || requestedPassword.isEmpty()) {
            requestedPassword = member.getPassword(); // 이전 비밀번호 사용
        } else {
            requestedPassword = passwordEncoder.encode(requestedPassword); // 새 비밀번호 인코딩
        }
        return requestedPassword;
    }
}
