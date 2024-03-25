package com.core.miniproject.src.accommodation.domain.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {

    private Long accommodationId;
    private Long discountId;
    private double discountRate;

}
