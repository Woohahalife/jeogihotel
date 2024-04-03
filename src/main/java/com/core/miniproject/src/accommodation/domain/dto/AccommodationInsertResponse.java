package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.image.domain.dto.ImageResponse;
import com.core.miniproject.src.location.domain.dto.LocationResponse;
import com.core.miniproject.src.location.domain.entity.Location;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationInsertResponse {

    private String accommodationName;
    private String accommodationType;
    private String introduction;
    private double rate;
    private double discount;
    private int price;
    private String address;
    private LocationResponse location;
    private List<ImageResponse> accommodationImage;

    public static AccommodationInsertResponse toClient(Accommodation accommodation) {
        return AccommodationInsertResponse.builder()
                .accommodationName(accommodation.getAccommodationName())
                .accommodationType(accommodation.getAccommodationType().getType())
                .introduction(accommodation.getIntroduction())
                .rate(accommodation.getAverageRate())
                .discount(accommodation.getDiscount().getDiscountRate())
                .price(accommodation.getMinPrice())
                .address(accommodation.getAddress())
                .location(LocationResponse.toClient(accommodation.getLocation()))
                .accommodationImage(accommodation.getImages().stream()
                        .map(ImageResponse::toClient)
                        .collect(Collectors.toList()))
                .build();
    }
}
