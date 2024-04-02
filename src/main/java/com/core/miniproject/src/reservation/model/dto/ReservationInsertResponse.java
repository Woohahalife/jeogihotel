package com.core.miniproject.src.reservation.model.dto;

import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInsertResponse {

    private String accommodationName;
    private String roomName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int price;
    private double discount;
    private int fixedNumber;
    private int maxedNumber;
    private IsVisited isVisited;

    public static ReservationInsertResponse toClient(Reservation reservation) {
        return ReservationInsertResponse.builder()
                .accommodationName(reservation.getRoom().getAccommodationId().getAccommodationName())
                .roomName(reservation.getRoomName())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .price(reservation.getPrice())
                .discount(reservation.getRoom().getAccommodationId().getDiscount().getDiscountRate())
                .fixedNumber(reservation.getFixedNumber())
                .maxedNumber(reservation.getMaxedNumber())
                .isVisited(reservation.getIsVisited())
                .build();
    }
}
