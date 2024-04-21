package com.core.miniproject.src.accommodation.domain.dto;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.image.domain.dto.ImageResponse;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredAccommodationDto {

    private Long id; //id 추가
    private Long memberId;
    private Long totalCount;
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

    public static RegisteredAccommodationDto toResponse(Accommodation accommodation){
        return RegisteredAccommodationDto.builder()
                .id(accommodation.getId())
                .memberId(accommodation.getMemberId())
                .accommodationName(accommodation.getAccommodationName())
                .accommodationType(accommodation.getAccommodationType().getType())
                .introduction(accommodation.getIntroduction())
                .locationType(accommodation.getLocation().getLocationName().getType())
                .rate(accommodation.getAverageRate()) // 별점 평균 조회
                .discount(accommodation.getDiscount().getDiscountRate())
                .address(accommodation.getAddress())
                .price(accommodation.getMinPrice())
                .accommodationImage(accommodation.getImages().stream()
                        .map(ImageResponse::toClient)
                        .collect(Collectors.toList()))
                .room(accommodation.getRoomId().stream()
                        .map(RoomResponse::toClient)
                        .collect(Collectors.toList()))
                .build();
    }

}
