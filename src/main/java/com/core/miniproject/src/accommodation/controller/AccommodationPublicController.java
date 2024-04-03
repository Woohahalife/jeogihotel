package com.core.miniproject.src.accommodation.controller;

import com.core.miniproject.src.accommodation.domain.dto.AccommodationAllResponse;
import com.core.miniproject.src.accommodation.domain.dto.AccommodationResponse;
import com.core.miniproject.src.accommodation.service.AccommodationService;
import com.core.miniproject.src.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public-api")
@Tag(name = "숙소 조회 api", description = "숙소 관련 api - 보안 설정 X")
public class AccommodationPublicController {

    private final AccommodationService accommodationService;

    @GetMapping("/v1/accommodation/all")
    public BaseResponse<List<AccommodationResponse>> getAllAccommodation() {

        List<AccommodationResponse> allAccommodation = accommodationService.getAllAccommodation();

        return BaseResponse.response(allAccommodation);
    }

    @GetMapping("/v1/accommodation")
    public BaseResponse<AccommodationAllResponse> findAccommodation(
            @RequestParam(name = "checkIn", required = false) LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false) LocalDate checkInOut,
            @RequestParam(name = "location_type", required = false) String locationType,
            @RequestParam(name = "accommodation_type", required = false) String accommodationType,
            @RequestParam(name = "personal", required = false) Integer personal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);

        AccommodationAllResponse allAccommodation = accommodationService.findAccommodation(checkIn, checkInOut, locationType, accommodationType, personal, pageable);

        return BaseResponse.response(allAccommodation);
    }

    @GetMapping("/v1/accommodation/{accommodation_id}/detail")
    public BaseResponse<AccommodationResponse> getAccommodationDetail(
            @RequestParam(name = "checkIn", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(1)}") LocalDate checkOut,
            @PathVariable("accommodation_id") Long accommodationId
    ) {

        AccommodationResponse response = accommodationService.getAccommodationDetail(accommodationId, checkIn, checkOut);

        return BaseResponse.response(response);
    }
}
