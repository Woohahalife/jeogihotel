package com.core.miniproject.src.common.security;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.common.security.principal.MemberPrincipal;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthMemberResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtAuthentication.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자가 인증되지 않은 경우 예외 처리
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new BaseException(NOT_AUTHENTICATED_USER);
        }

        MemberPrincipal memberPrincipal = memberRepository.findByMemberEmail(userDetails.getUsername())
                .map(MemberPrincipal::new)
                .orElseThrow(() -> new BaseException(EMAIL_NOT_FOUND));

        return MemberInfo
                .toParameter(memberPrincipal.getMember()
                        .orElseThrow(() -> new BaseException(PRINCIPAL_IS_NOT_FOUND)));
    }
}
