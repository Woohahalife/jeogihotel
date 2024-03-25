package com.core.miniproject.src.reservation.service;

import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import com.core.miniproject.src.reservation.model.dto.ReservationInsertRequest;
import com.core.miniproject.src.reservation.model.dto.ReservationInsertResponse;
import com.core.miniproject.src.reservation.model.dto.ReservationListResponse;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import com.core.miniproject.src.reservation.repository.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ReservationRepository reservationRepository;

    MemberInfo memberInfo = new MemberInfo(1L, "string", Role.USER);
    Member member = Member.builder()
            .email(memberInfo.getEmail())
            .name("name")
            .password("password")
            .phoneNumber("phoneNumber")
            .build();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        reservationService = new ReservationService(memberRepository, reservationRepository);
    }

    @Test
    void reservaion_저장_성공() {
        // given
        ReservationInsertRequest request = new ReservationInsertRequest("객실1", LocalDate.now(), LocalDate.now().plusDays(1), 20000, 2, 4);

        BDDMockito.given(memberRepository.findByMemberEmail(any())).willReturn(Optional.of(member)); // memberInfo의 getEmail을 사용하기 때문에 주입되는 파라미터를 정의함

        Reservation expectedReservation = Reservation.builder()
                .roomName(request.getRoomName())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .fixedNumber(request.getFixedMember())
                .maxedNumber(request.getMaxedMember())
                .isVisited(IsVisited.VISITED)
                .build();

        BDDMockito.given(reservationRepository.save(argThat(reservation -> reservation.getRoomName().equals("객실1"))))
                .willReturn(expectedReservation);

        // when
        ReservationInsertResponse reservationInsertResponse = reservationService.insertReservation(request, memberInfo);

        // then
        BDDMockito.verify(memberRepository).findByMemberEmail(memberInfo.getEmail());
        BDDMockito.verify(reservationRepository).save(argThat(reservation -> reservation.getRoomName().equals("객실1")));
        assertThat(reservationInsertResponse.getRoomName()).isEqualTo(expectedReservation.getRoomName());
        assertThat(reservationInsertResponse.getCheckIn()).isEqualTo(expectedReservation.getCheckIn());
        assertThat(reservationInsertResponse.getCheckOut()).isEqualTo(expectedReservation.getCheckOut());
        assertThat(reservationInsertResponse.getFixedNumber()).isEqualTo(expectedReservation.getFixedNumber());
        assertThat(reservationInsertResponse.getMaxedNumber()).isEqualTo(expectedReservation.getMaxedNumber());
        assertThat(reservationInsertResponse.getIsVisited()).isEqualTo(expectedReservation.getIsVisited());
    }

    @Test
    void insertReservationValidate_유효하지_않은_날짜_예외_발생() {
        ReservationInsertRequest invalidRequest = new ReservationInsertRequest("객실1", LocalDate.now(), LocalDate.now(), 20000, 2, 4);

        BDDMockito.given(memberRepository.findByMemberEmail(memberInfo.getEmail())).willReturn(Optional.of(member));

        assertThatThrownBy(() -> reservationService.insertReservation(invalidRequest, memberInfo))
                .isInstanceOf(BaseException.class)
                .hasMessage(INVALID_DATE_SETTING.getMessage());
    }

    @Test
    void 모든_reservation_조회_성공() {
        // given
        ReservationInsertRequest request1 = new ReservationInsertRequest("객실1", LocalDate.now(), LocalDate.now().plusDays(1), 20000, 2, 4);
        ReservationInsertRequest request2 = new ReservationInsertRequest("객실2", LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 20000, 2, 4);

        BDDMockito.given(memberRepository.findByMemberEmail(memberInfo.getEmail())).willReturn(Optional.of(member));

        Reservation expectedReservation1 = Reservation.builder()
                .roomName(request1.getRoomName())
                .checkIn(request1.getCheckIn())
                .checkOut(request1.getCheckOut())
                .fixedNumber(request1.getFixedMember())
                .maxedNumber(request1.getMaxedMember())
                .isVisited(IsVisited.VISITED)
                .build();

        Reservation expectedReservation2 = Reservation.builder()
                .roomName(request2.getRoomName())
                .checkIn(request2.getCheckIn())
                .checkOut(request2.getCheckOut())
                .fixedNumber(request2.getFixedMember())
                .maxedNumber(request2.getMaxedMember())
                .isVisited(IsVisited.VISITED)
                .build();

        BDDMockito.given(reservationRepository.findAllReservation(member.getId())).willReturn(Arrays.asList(expectedReservation1, expectedReservation2));

        // when
        List<ReservationListResponse> allReservation = reservationService.findAllReservation(memberInfo);

        // given
        BDDMockito.verify(memberRepository).findByMemberEmail(memberInfo.getEmail());
        BDDMockito.verify(reservationRepository).findAllReservation(member.getId());
        assertThat(allReservation).isNotNull();
        assertThat(allReservation).hasSize(2);
    }

    @Test
    void 빈_데이터인_경우에도_정상_전달() {
        // given
        BDDMockito.given(memberRepository.findByMemberEmail(memberInfo.getEmail())).willReturn(Optional.of(member));
        BDDMockito.given(reservationRepository.findAllReservation(member.getId())).willReturn(Collections.emptyList());

        // when
        List<ReservationListResponse> allReservation = reservationService.findAllReservation(memberInfo);

        // given
        BDDMockito.verify(memberRepository).findByMemberEmail(memberInfo.getEmail());
        BDDMockito.verify(reservationRepository).findAllReservation(member.getId());
        assertThat(allReservation).isEmpty();
        assertThat(allReservation).hasSize(0);
    }

    @Test
    void 이메일을_찾지_못함() {

        // when & then
        BDDMockito.when(memberRepository.findByMemberEmail("string")).thenReturn(Optional.empty());

        assertThatExceptionOfType(BaseException.class)
                .isThrownBy(() -> reservationService.findAllReservation(memberInfo))
                .withMessageContaining(EMAIL_NOT_FOUND.getMessage());
    }

    @Test // 아직 실패 케이스
    void 이메일이_다름() {

        Member member = Member.builder()
                .email("another")
                .name("name")
                .password("password")
                .phoneNumber("phoneNumber")
                .build();

        // when & then
        BDDMockito.when(memberRepository.findByMemberEmail("string")).thenReturn(Optional.of(member));

        assertThatExceptionOfType(BaseException.class)
                .isThrownBy(() -> reservationService.findAllReservation(memberInfo))
                .withMessageContaining(EMAIL_IS_NOT_VALIDATE.getMessage());
    }
}