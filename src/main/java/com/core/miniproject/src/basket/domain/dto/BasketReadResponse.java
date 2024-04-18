package com.core.miniproject.src.basket.domain.dto;

import com.core.miniproject.src.basket.domain.entity.Basket;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BasketReadResponse {

    private Long id;
    private Long roomId;
    private String roomName;
    private String address;
    private int fixedNumber;
    private int maxedNumber;
    private int price;
    private double disCount;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String basketImage;


    public static BasketReadResponse toClient(Basket basket) {
        return BasketReadResponse.builder()
                .id(basket.getId())
                .roomId(basket.getRoom().getId())
                .roomName(basket.getRoomName())
                .address(basket.getAddress())
                .fixedNumber(basket.getFixedNumber())
                .maxedNumber(basket.getMaxedNumber())
                .price(basket.getPrice())
                .disCount(basket.getDiscount())
                .checkIn(basket.getCheckIn())
                .checkOut(basket.getCheckOut())
                .basketImage(basket.getBasketImage())
                .build();
    }
}
