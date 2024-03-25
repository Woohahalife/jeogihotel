package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.dto.LocationResponse;
import com.core.miniproject.src.location.domain.entity.Location;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationInsertResponse {

    private String accommodationName;
    private AccommodationType accommodationType;
    private String accommodationImage;
    private String introduction;
    private double rate;
    private double discount;
    private int price;
    private LocationResponse location;

    public static AccommodationInsertResponse toClient(Accommodation accommodation) {
        return AccommodationInsertResponse.builder()
                .accommodationName(accommodation.getAccommodationName())
                .accommodationType(accommodation.getAccommodationType())
                .accommodationImage(accommodation.getAccommodationImage())
                .introduction(accommodation.getIntroduction())
                .rate(accommodation.getRate())
                .discount(accommodation.getDiscount().getDiscountRate())
                .price(accommodation.getPrice())
                .location(LocationResponse.toClient(accommodation.getLocation()))
                .build();
    }
}
