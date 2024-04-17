package com.core.miniproject.src.reservation.service;

import com.core.miniproject.src.basket.domain.entity.Basket;
import com.core.miniproject.src.basket.repository.BasketRepository;
import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import com.core.miniproject.src.reservation.model.dto.ReservationBasketRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final BasketRepository basketRepository;

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

        List<Reservation> reservation = reservationRepository.findReservationByCheckInAndCheckOutAndId(
                request.getCheckIn(), request.getCheckOut(), room.getId());

        if (!reservation.isEmpty()) {
            throw new BaseException(RESERVATION_IS_DUPLICATE);
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

    @Transactional
    public List<ReservationListResponse> reservationFromBasket(ReservationBasketRequest request, MemberInfo memberInfo) {

        List<Long> baskIds = request.getBaskIds();
        // 해당 회원의 장바구니에 담긴 데이터를 예약쪽으로 전달
        List<Basket> selectedBasketList = basketRepository.findAllByMemberIdAndIdIn(memberInfo.getId(), baskIds);

        List<Reservation> resultReservation = new ArrayList<>();
        // 장바구니에 담긴 객실이 현재 시점에 예약이 되어있는지 다시한번 체크
        for (Basket basket : selectedBasketList) {
            Long roomId = basket.getRoom().getId();

            List<Reservation> reservation = reservationRepository.findReservationByCheckInAndCheckOutAndId(
                    basket.getCheckIn(), basket.getCheckOut(), roomId
            );

            if (reservation.isEmpty()) { // 예약 중복이 없는 경우에만 필터링

                Reservation filteredReservation = Reservation.builder()
                        .member(basket.getMember())
                        .roomName(basket.getRoomName())
                        .address(basket.getAddress())
                        .checkIn(basket.getCheckIn())
                        .checkOut(basket.getCheckOut())
                        .price(basket.getPrice())
                        .fixedNumber(basket.getFixedNumber())
                        .maxedNumber(basket.getMaxedNumber())
                        .isVisited(IsVisited.NOT_VISIT)
                        .room(basket.getRoom())
                        .build();

                resultReservation.add(filteredReservation);

                basketRepository.deleteById(basket.getId()); // 예약 리스트 안에 들어간 장바구니 데이터 삭제
            }else {

                basketRepository.deleteById(basket.getId()); // 예약 시점 중복된 장바구니 데이터 삭제
            }
        }

        List<Reservation> reservationList = reservationRepository.saveAll(resultReservation);

        return reservationList
                .stream()
                .map(ReservationListResponse::toClient)
                .collect(Collectors.toList());
    }
}
