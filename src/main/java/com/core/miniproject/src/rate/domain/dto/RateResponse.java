package com.core.miniproject.src.rate.domain.dto;

import com.core.miniproject.src.rate.domain.entity.Rate;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateResponse {
    double rate;

    public static RateResponse toClient(Rate rate){
        return RateResponse.builder()
                .rate(rate.getRate())
                .build();
    }
}
