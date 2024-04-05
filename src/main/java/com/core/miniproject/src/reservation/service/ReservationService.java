package com.core.miniproject.src.reservation.service;

import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import com.core.miniproject.src.reservation.model.dto.ReservationInsertRequest;
import com.core.miniproject.src.reservation.model.dto.ReservationInsertResponse;
import com.core.miniproject.src.reservation.model.dto.ReservationListResponse;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import com.core.miniproject.src.reservation.repository.ReservationRepository;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationInsertResponse registerReservation(ReservationInsertRequest request, MemberInfo memberInfo) {

        Reservation reservation = getReservationFromRequest(request, memberInfo);

        return ReservationInsertResponse.toClient(reservationRepository.save(reservation));
    }

    private Reservation getReservationFromRequest(ReservationInsertRequest request, MemberInfo memberInfo) {
        insertReservationValidate(request); // checkIn & checkOut 논리 검증
        Member member = emailValidate(memberInfo); // 회원 일치 여부 검증

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(ROOM_NOT_FOUND));

        Reservation reservation = reservationRepository.findReservationByCheckInAndCheckOutAndId(
                request.getCheckIn(), request.getCheckOut(), room.getId());

        if(reservation != null) {
            throw new BaseException(RESERVAION_IS_DUPLICATE);
        }

        return Reservation.builder()
                .member(member)
                .roomName(request.getRoomName())
                .address(request.getAddress())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .price(room.getPrice())
                .fixedNumber(request.getFixedMember())
                .maxedNumber(request.getMaxedMember())
                .isVisited(IsVisited.NOT_VISIT)
                .room(room) // 연관관계 매핑
                .build();
    }

    @Transactional
    public List<ReservationListResponse> findAllReservation(MemberInfo memberInfo) {

        Member member = emailValidate(memberInfo);

        isVisitValidate();

        List<Reservation> allReservation =
                reservationRepository.findAllReservation(member.getId());


        return allReservation.stream()
                .map(ReservationListResponse::toClient)
                .collect(Collectors.toList());
    }

    private void isVisitValidate() {// 체크인 시간과 현재 날짜를 비교해 방문여부 전환
        reservationRepository.updateReservationOverDue(IsVisited.OVERDUE);
        reservationRepository.updateOnReservationDay(IsVisited.VISIT_DATE);
    }

    private void insertReservationValidate(ReservationInsertRequest request) { // 체크인 & 체크아웃 날짜 논리 검증
        if (request.getCheckIn().equals(request.getCheckOut()) ||
            request.getCheckOut().isBefore(request.getCheckIn())) {

            throw new BaseException(INVALID_DATE_SETTING);
        }
    }

    private Member emailValidate(MemberInfo memberInfo) {
        Member member = memberRepository.findByMemberEmail(memberInfo.getEmail())
                .orElseThrow(() -> new BaseException(EMAIL_NOT_FOUND));

        if (!memberInfo.getEmail().equals(member.getEmail())) {
            throw new BaseException(EMAIL_IS_NOT_VALIDATE);
        }

        return member;
    }
}
