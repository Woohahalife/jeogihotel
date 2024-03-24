package com.core.miniproject.src.reservation.model.entity;

import com.core.miniproject.src.common.constant.IsVisited;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    private int price;

    @Column(name = "fixed_number")
    private int fixedNumber;

    @Column(name = "maxed_number")
    private int maxedNumber;

    @Column(name = "is_visited")
    private IsVisited isVisited;

}
