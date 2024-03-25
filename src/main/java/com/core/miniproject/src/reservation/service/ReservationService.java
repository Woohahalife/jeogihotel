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
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationInsertResponse insertReservation(ReservationInsertRequest request, MemberInfo memberInfo) {

        Member member = memberRepository.findByMemberEmail(memberInfo.getEmail())
                .orElseThrow(() -> new BaseException(EMAIL_NOT_FOUND));

        Reservation reservation = getReservationFromRequest(request, member);

        return ReservationInsertResponse.toClient(reservationRepository.save(reservation));
    }

    private Reservation getReservationFromRequest(ReservationInsertRequest request, Member member) {
        insertReservationValidate(request);

        return Reservation.builder()
                .member(member)
                .roomName(request.getRoomName())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .price(request.getPrice())
                .fixedNumber(request.getFixedMember())
                .maxedNumber(request.getMaxedMember())
                .isVisited(IsVisited.VISITED)
                .build();
    }

    public List<ReservationListResponse> findAllReservation(MemberInfo memberInfo) {

        Member member = emailValidate(memberInfo);
        List<Reservation> allReservation =
                reservationRepository.findAllReservation(member.getId());

        return allReservation.stream()
                .map(ReservationListResponse::toClient)
                .collect(Collectors.toList());
    }

    private void insertReservationValidate(ReservationInsertRequest request) {
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
