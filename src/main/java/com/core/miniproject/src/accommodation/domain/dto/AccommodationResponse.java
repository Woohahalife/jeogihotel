package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import com.core.miniproject.src.room.domain.entity.Room;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private double discount;
    private String address;
    private int price;
    private List<RoomResponse> room;

    public static AccommodationResponse toClient(Accommodation accommodation){
        return AccommodationResponse.builder()
                .id(accommodation.getId())
                .accommodationName(accommodation.getAccommodationName())
                .accommodationType(accommodation.getAccommodationType())
                .accommodationImage(accommodation.getAccommodationImage())
                .introduction(accommodation.getIntroduction())
                .locationType(accommodation.getLocation().getLocationName())
                .rate(accommodation.getAverageRate()) // 별점 평균 조회
                .discount(accommodation.getDiscount().getDiscountRate())
                .address(accommodation.getAddress())
                .price(accommodation.getMinPrice())
                .room(accommodation.getRoomId().stream()
                        .map(RoomResponse::toClient)
                        .collect(Collectors.toList()))
                .build();
    }
}
