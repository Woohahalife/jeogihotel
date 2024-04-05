package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.image.domain.dto.ImageResponse;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationAllDto {

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

    public static AccommodationAllDto toClient(Accommodation accommodation){
        return AccommodationAllDto.builder()
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
