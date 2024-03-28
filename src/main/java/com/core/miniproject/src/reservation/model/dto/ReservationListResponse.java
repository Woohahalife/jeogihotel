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
public class ReservationListResponse {

    private Long memberId;
    private String roomName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int price;
    private int fixedNumber;
    private int maxedNumber;
    private IsVisited isVisited;
    private RoomResponse room;

    public static ReservationListResponse toClient(Reservation reservation) {
        return ReservationListResponse.builder()
                .memberId(reservation.getMember().getId())
                .roomName(reservation.getRoomName())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .price(reservation.getPrice())
                .fixedNumber(reservation.getFixedNumber())
                .maxedNumber(reservation.getMaxedNumber())
                .isVisited(reservation.getIsVisited())
                .room(RoomResponse.toClient(reservation.getRoom()))
                .build();
    }
}
