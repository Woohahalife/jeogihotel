package com.core.miniproject.src.basket.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class BasketDeleteRequest {

    private List<Long> baskIds;

    public BasketDeleteRequest(List<Long> baskIds) {
        this.baskIds = baskIds;
    }
}
