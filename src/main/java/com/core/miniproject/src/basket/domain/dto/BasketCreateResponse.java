package com.core.miniproject.src.basket.domain.dto;

import com.core.miniproject.src.basket.domain.entity.Basket;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BasketCreateResponse {

    private Long id;
    private Long memberId;
    private Long roomId;
    private String roomName;
    private String address;
    private int fixedNumber;
    private int maxedNumber;
    private int price;
    private double discount;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public static BasketCreateResponse toClient(Basket savedBasket) {
        return BasketCreateResponse.builder()
                .id(savedBasket.getId())
                .memberId(savedBasket.getMember().getId())
                .roomId(savedBasket.getRoom().getId())
                .roomName(savedBasket.getRoomName())
                .address(savedBasket.getAddress())
                .fixedNumber(savedBasket.getFixedNumber())
                .maxedNumber(savedBasket.getMaxedNumber())
                .price(savedBasket.getPrice())
                .discount(savedBasket.getDiscount())
                .checkIn(savedBasket.getCheckIn())
                .checkOut(savedBasket.getCheckOut())
                .build();
    }
}
