package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationResponse {

    private Long id; //id 추가
    private String accommodationName;
    private AccommodationType accommodationType;
    private String accommodationImage;
    private String introduction;
    private LocationType locationType; //위치 추가
    private double rate;
    private int price;

    public static AccommodationResponse toClient(Accommodation accommodation){
        return AccommodationResponse.builder()
                .id(accommodation.getId())
                .accommodationName(accommodation.getAccommodationName())
                .accommodationType(accommodation.getAccommodationType())
                .accommodationImage(accommodation.getAccommodationImage())
                .introduction(accommodation.getIntroduction())
                .locationType(accommodation.getLocation().getLocationName())
                .rate(accommodation.getAverageRate()) // 별점 평균 조회
                .price(accommodation.getPrice())
                .build();
    }


}
