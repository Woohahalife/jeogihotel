package com.core.miniproject.src.reservation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInsertRequest {

    private Long roomId; // 예약을 하려는 객실 id값
    private String roomName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int fixedMember;
    private int maxedMember;

}
