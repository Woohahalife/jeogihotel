package com.core.miniproject.src.accommodation.controller;

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

    @GetMapping("/v1/test/{location_type}/{accommodation_type}/{personal}")
    public void test(
            @RequestParam(name = "checkIn", required = false) LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false) LocalDate checkInOut,
            @RequestParam(name = "location_type", required = false) String locationType,
            @RequestParam(name = "accommodation_type", required = false) String accommodationType,
            @RequestParam(name = "personal", required = false) Integer personal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {

        System.out.println("checkIn = " + checkIn);
        System.out.println("checkInOut = " + checkInOut);
        System.out.println("locationType = " + locationType);
        System.out.println("accommodationType = " + accommodationType);
        System.out.println("fixedMember = " + personal);
    }

    @GetMapping("/v1/accommodation")
    public BaseResponse<List<AccommodationResponse>> findAll(
            @RequestParam(name = "checkIn", required = false) LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false) LocalDate checkInOut,
            @RequestParam(name = "location_type", required = false) String locationType,
            @RequestParam(name = "accommodation_type", required = false) String accommodationType,
            @RequestParam(name = "personal", required = false) Integer personal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<AccommodationResponse> responses = accommodationService.findAllAccommodation(checkIn, checkInOut, pageable);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/location")
    public BaseResponse<List<AccommodationResponse>> findByLocation(
            @RequestParam(name = "checkIn", required = false) LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false) LocalDate checkInOut,
            @RequestParam(name = "location_type", required = false) String locationType,
            @RequestParam(name = "accommodation_type", required = false) String accommodationType,
            @RequestParam(name = "personal", required = false) Integer personal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<AccommodationResponse> responses = accommodationService.findAccommodationByLocation(locationType, checkIn, checkInOut, pageable);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/type")
    public BaseResponse<List<AccommodationResponse>> findAccommodationByType(
            @RequestParam(name = "checkIn", required = false) LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false) LocalDate checkInOut,
            @RequestParam(name = "location_type", required = false) String locationType,
            @RequestParam(name = "accommodation_type", required = false) String accommodationType,
            @RequestParam(name = "personal", required = false) Integer personal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        List<AccommodationResponse> responses = accommodationService.findAccommodationByType(accommodationType, checkIn, checkInOut, pageable);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/type/{accommodation_type}/location/{location_type}")
    public BaseResponse<List<AccommodationResponse>> findByType(
            @RequestParam(name = "checkIn", required = false) LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false) LocalDate checkInOut,
            @RequestParam(name = "location_type", required = false) String locationType,
            @RequestParam(name = "accommodation_type", required = false) String accommodationType,
            @RequestParam(name = "personal", required = false) Integer personal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        List<AccommodationResponse> responses = accommodationService.findByAccommodationAndLocation(accommodationType, locationType, checkIn, checkInOut, pageable);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/location/personal")
    public BaseResponse<List<AccommodationResponse>> findByLocationAndPersonal(
            @RequestParam(name = "checkIn", required = false) LocalDate checkIn,
            @RequestParam(name = "checkOut", required = false) LocalDate checkInOut,
            @RequestParam(name = "location_type", required = false) String locationType,
            @RequestParam(name = "accommodation_type", required = false) String accommodationType,
            @RequestParam(name = "personal", required = false) Integer personal,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<AccommodationResponse> responses = accommodationService.findByLocationAndPersonal(locationType, personal, checkIn, checkInOut, pageable);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/{accommodation_id}/detail")
    public BaseResponse<AccommodationResponse> getAccommodationDetail(
            @PathVariable("accommodation_id") Long accommodationId
    ) {
        AccommodationResponse response = accommodationService.getAccommodationDetail(accommodationId);

        return BaseResponse.response(response);
    }
}
