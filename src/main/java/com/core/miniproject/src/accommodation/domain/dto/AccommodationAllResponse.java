package com.core.miniproject.src.accommodation.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationAllResponse {

    private Integer accommodationCount;
    List<AccommodationAllDto> result;

    public static AccommodationAllResponse toClient(List<AccommodationAllDto> dto, Integer count) {
        return AccommodationAllResponse.builder()
                .accommodationCount(count)
                .result(dto)
                .build();
    }
}
