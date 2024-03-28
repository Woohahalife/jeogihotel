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

        Member member = memberRepository.findByMemberEmail(memberInfo.getEmail())
                .orElseThrow(() -> new BaseException(EMAIL_NOT_FOUND));

        Reservation reservation = getReservationFromRequest(request, member);

        return ReservationInsertResponse.toClient(reservationRepository.save(reservation));
    }

    private Reservation getReservationFromRequest(ReservationInsertRequest request, Member member) {
        insertReservationValidate(request);

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(ROOM_NOT_FOUND));

        // 검색을 했을 때 해당 객실에 예약 내역이 있으면 roomCount -1 이외의 날짜에는 roomCount가 초기상태

        if(room.getRoomCount() == 0) { // 해당 객실의 잔여 개수가 0
            throw new BaseException(NO_ROOMS_REMAINING);
        }

        return Reservation.builder()
                .member(member)
                .roomName(request.getRoomName())
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

        if(!memberInfo.getEmail().equals(member.getEmail())) {
            throw new BaseException(EMAIL_IS_NOT_VALIDATE);
        }

        return member;
    }
}
