package com.core.miniproject.src.member.repository;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.member.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 멤버_정보_생성() {

        // given
        Member member = Member.builder()
                .email("chlrjs132")
                .password("alrtm1324")
                .phoneNumber("01042421075")
                .build();

        // when
        Member save = memberRepository.save(member);

        // then
        assertThat(save.getId()).isEqualTo(1);
        assertThat(save.getEmail()).isEqualTo(member.getEmail());
        assertThat(save.getPassword()).isEqualTo(member.getPassword());
        assertThat(save.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
    }

    @Test
    void 특정_회원_이메일_기반_회원정보_조회() {

        // given
        Member member = Member.builder()
                .email("chlrjs132@naver.com")
                .password("alrtm1324")
                .phoneNumber("01042421075")
                .build();

        Member saveMember = memberRepository.save(member);

        // when
        Member memberEmail = memberRepository.findByMemberEmail(member.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.EMAIL_NOT_FOUND));

        // then
        assertThat(memberEmail.getEmail()).isEqualTo(saveMember.getEmail());
        assertThat(memberEmail.getPassword()).isEqualTo(saveMember.getPassword());
        assertThat(memberEmail.getPhoneNumber()).isEqualTo(saveMember.getPhoneNumber());
    }

    @Test
    void 조회한_이메일이_없는_경우_예외처리() {
        // Given
        String email = "nonexistent@example.com";

        // When, Then
        Assertions.assertThatThrownBy(() -> memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.EMAIL_NOT_FOUND)))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("status", BaseResponseStatus.EMAIL_NOT_FOUND);
    }
}
