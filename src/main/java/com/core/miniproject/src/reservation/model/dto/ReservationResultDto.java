package com.core.miniproject.src.reservation.model.dto;

import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResultDto {

    private Long reservationId;
    private Long memberId;
    private String accommodationName;
    private String roomName;
    private String address;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int price;
    private double discount;
    private int fixedNumber;
    private int maxedNumber;
    private IsVisited isVisited;
    private RoomResponse room;

    public static ReservationResultDto toResponse(Reservation reservation) {
        return ReservationResultDto.builder()
                .reservationId(reservation.getId())
                .memberId(reservation.getMember().getId())
                .accommodationName(reservation.getRoom().getAccommodationId().getAccommodationName())
                .roomName(reservation.getRoomName())
                .address(reservation.getAddress())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .price(reservation.getPrice())
                .discount(reservation.getRoom().getAccommodationId().getDiscount().getDiscountRate())
                .fixedNumber(reservation.getFixedNumber())
                .maxedNumber(reservation.getMaxedNumber())
                .isVisited(reservation.getIsVisited())
                .room(RoomResponse.toClient(reservation.getRoom()))
                .build();
    }
}
