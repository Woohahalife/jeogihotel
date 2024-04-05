package com.core.miniproject.src.rate.domain.dto;

import com.core.miniproject.src.rate.domain.entity.Rate;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateInsertResponse {

    private double rate;

    public static RateInsertResponse toClient(Rate rate){
        return RateInsertResponse.builder()
                .rate(rate.getRate())
                .build();
    }
}
