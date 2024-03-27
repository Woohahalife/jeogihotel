package com.core.miniproject.src.accommodation.controller;

import com.core.miniproject.src.accommodation.domain.dto.AccommodationResponse;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.accommodation.service.AccommodationService;
import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.location.domain.entity.LocationType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public-api")
@Tag(name = "숙소 조회 api", description = "숙소 관련 api - 보안 설정 X")
public class AccommodationPublicController {

    private final AccommodationService accommodationService;

    @GetMapping("/v1/accommodation")
    public BaseResponse<List<AccommodationResponse>> findAll(){
        List<AccommodationResponse> responses = accommodationService.findAllAccommodation();

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/location/{location_type}")
    public BaseResponse<List<AccommodationResponse>> findByLocation(
            @PathVariable("location_type")LocationType locationType
    ){
        List<AccommodationResponse> responses = accommodationService.findAccommodationByLocation(locationType);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/type/{accommodation_type}")
    public BaseResponse<List<AccommodationResponse>> findByType(
            @PathVariable("accommodation_type") AccommodationType accommodationType
    ){
        List<AccommodationResponse> responses = accommodationService.findAccommodationByType(accommodationType);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/type/{accommodation_type}/location/{location_type}")
    public BaseResponse<List<AccommodationResponse>> findByType(
            @PathVariable("accommodation_type") AccommodationType accommodationType,
            @PathVariable("location_type") LocationType locationType
    ){
        List<AccommodationResponse> responses = accommodationService.findByAccommodationAndLocation(accommodationType, locationType);

        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/accommodation/location/{location_type}/personal/{personal}")
    public BaseResponse<List<AccommodationResponse>> findByLocationAndPersonal(
            @PathVariable("location_type") LocationType locationType,
            @PathVariable("personal") int fixedMember
    ){
        List<AccommodationResponse> responses = accommodationService.findByLocationAndPersonal(locationType, fixedMember);

        return BaseResponse.response(responses);
    }

}
