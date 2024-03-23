package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.room.domain.entity.Room;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationResponse {

    private String accommodationName;
    private AccommodationType accommodationType;
    private String accommodationImage;
    private String introduction;
    private double rate;
    private int price;

    public static AccommodationResponse toClient(Accommodation accommodation){
        return AccommodationResponse.builder()
                .accommodationName(accommodation.getAccommodationName())
                .accommodationType(accommodation.getAccommodationType())
                .accommodationImage(accommodation.getAccommodationImage())
                .introduction(accommodation.getIntroduction())
                .rate(accommodation.getRate())
                .price(accommodation.getPrice())
                .build();
    }


}
