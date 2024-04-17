package com.core.miniproject.src.reservation.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ReservationBasketRequest {

    private List<Long> baskIds;

    public ReservationBasketRequest(List<Long> baskIds) {
        this.baskIds = baskIds;
    }
}
