package com.core.miniproject.src.basket.domain.entity;

import com.core.miniproject.src.basket.domain.dto.BasketCreateRequest;
import com.core.miniproject.src.common.entity.BaseEntity;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.room.domain.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "address")
    private String address;

    @Column(name = "fixed_number")
    private int fixedNumber;

    @Column(name = "maxed_number")
    private int maxedNumber;

    @Column(name = "price")
    private int price;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    private String basketImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public static Basket create(Member member, Room room, BasketCreateRequest request) {
        return Basket.builder()
                .roomName(room.getRoomName())
                .address(room.getAccommodationId().getAddress())
                .fixedNumber(room.getFixedMember())
                .maxedNumber(room.getMaxedMember())
                .price(room.getPrice())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .basketImage(room.getRoomName())
                .member(member)
                .room(room)
                .build();
    }
}
