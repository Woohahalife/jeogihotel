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

    private String roomName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int price;
    private int fixedMember;
    private int maxedMember;

}
