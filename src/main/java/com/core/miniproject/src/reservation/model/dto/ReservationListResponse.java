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
public class ReservationListResponse {

    private Long memberId;
    private String roomName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int price;
    private int fixedNumber;
    private int maxedNumber;
    private IsVisited isVisited;

    public static ReservationListResponse toClient(Reservation reservation) {
        return ReservationListResponse.builder()
//                .memberId()
                .roomName(reservation.getRoomName())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .price(reservation.getPrice())
                .fixedNumber(reservation.getFixedNumber())
                .maxedNumber(reservation.getMaxedNumber())
                .isVisited(reservation.getIsVisited())
                .build();
    }
}
