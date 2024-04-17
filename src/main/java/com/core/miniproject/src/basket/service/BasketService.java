package com.core.miniproject.src.basket.service;

import com.core.miniproject.src.basket.domain.dto.BasketCreateRequest;
import com.core.miniproject.src.basket.domain.dto.BasketCreateResponse;
import com.core.miniproject.src.basket.domain.dto.BasketDeleteRequest;
import com.core.miniproject.src.basket.domain.dto.BasketReadResponse;
import com.core.miniproject.src.basket.domain.entity.Basket;
import com.core.miniproject.src.basket.repository.BasketRepository;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import com.core.miniproject.src.reservation.repository.ReservationRepository;
import com.core.miniproject.src.room.domain.entity.Room;
import com.core.miniproject.src.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class BasketService {

    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public BasketCreateResponse registerBasket(Long roomId, BasketCreateRequest request, MemberInfo memberInfo) {

        Member member = memberRepository.findById(memberInfo.getId())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new BaseException(ROOM_NOT_FOUND));

        List<Basket> basketList = basketRepository.findByRoomIdAndCheckInAndCheckOut(request.getCheckIn(), request.getCheckOut(), roomId);

        if(!basketList.isEmpty()) {
            throw new BaseException(BASKET_IS_DUPLICATE);
        }

        Basket basket = Basket.create(member, room, request);

        Basket savedBasket = basketRepository.save(basket);

        return BasketCreateResponse.toClient(savedBasket);
    }

    public List<BasketReadResponse> readAllBasket(MemberInfo memberInfo) {

        Member member = memberRepository.findById(memberInfo.getId())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<Basket> basketList = basketRepository.findAllBasketByMemberId(member.getId());

        return basketList.stream()
                .map(BasketReadResponse::toClient)
                .collect(Collectors.toList());
    }

    // 삭제할 데이터가 없는 경우 0 반환(예외처리 X)
    @Transactional
    public Integer deleteSelectedBasket(BasketDeleteRequest request, MemberInfo memberInfo) {

        Member member = memberRepository.findById(memberInfo.getId())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        return request.getBaskIds().stream()
                .mapToInt(basketId -> basketRepository.deleteBasketByMemberIdAndBasketId(member.getId(), basketId))
                .sum();
    }
}
