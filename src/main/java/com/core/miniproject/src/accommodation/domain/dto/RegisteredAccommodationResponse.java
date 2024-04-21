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
public class RegisteredAccommodationResponse {

    private Long totalCount;
    private List<RegisteredAccommodationDto> result;

    public static RegisteredAccommodationResponse toClient(List<RegisteredAccommodationDto> dto, Long count) {
        return RegisteredAccommodationResponse.builder()
                .totalCount(count)
                .result(dto)
                .build();
    }
}
