package com.core.miniproject.src.reservation.model.dto;

import com.core.miniproject.src.accommodation.domain.dto.RegisteredAccommodationDto;
import com.core.miniproject.src.accommodation.domain.dto.RegisteredAccommodationResponse;
import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResultResponse {

    private Long totalCount;
    private List<ReservationResultDto> result;

    public static ReservationResultResponse toClient(List<ReservationResultDto> dto, Long count) {
        return ReservationResultResponse.builder()
                .totalCount(count)
                .result(dto)
                .build();
    }
}
