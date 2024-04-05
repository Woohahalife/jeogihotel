package com.core.miniproject.src.common.security.principal;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.core.miniproject.src.common.response.BaseResponseStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberPrincipalService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByMemberEmail(email)
                .map(MemberPrincipal::new)
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));
    }
}
