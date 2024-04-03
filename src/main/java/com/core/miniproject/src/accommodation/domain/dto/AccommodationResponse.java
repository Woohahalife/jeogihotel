package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.image.domain.dto.ImageResponse;
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
    private String accommodationType;
    private String introduction;
    private String locationType; //위치 추가
    private Double rate;
    private Double discount;
    private String address;
    private Integer price;
    private List<ImageResponse> accommodationImage;
    private List<RoomResponse> room;

    public static AccommodationResponse toClient(Accommodation accommodation){
        return AccommodationResponse.builder()
                .id(accommodation.getId())
                .accommodationName(accommodation.getAccommodationName())
                .accommodationType(accommodation.getAccommodationType().getType())
                .introduction(accommodation.getIntroduction())
                .locationType(accommodation.getLocation().getLocationName().getType())
                .rate(accommodation.getAverageRate()) // 별점 평균 조회
                .discount(accommodation.getDiscount().getDiscountRate())
                .address(accommodation.getAddress())
                .price(accommodation.getMinPrice())
                .room(accommodation.getRoomId().stream()
                        .map(RoomResponse::toClient)
                        .collect(Collectors.toList()))
                .accommodationImage(accommodation.getImages().stream()
                        .map(ImageResponse::toClient)
                        .collect(Collectors.toList()))
                .build();
    }
}
