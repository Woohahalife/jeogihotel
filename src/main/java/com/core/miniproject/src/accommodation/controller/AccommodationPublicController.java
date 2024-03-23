package com.core.miniproject.src.accommodation.controller;

import com.core.miniproject.src.accommodation.domain.dto.AccommodationResponse;
import com.core.miniproject.src.accommodation.service.AccommodationService;
import com.core.miniproject.src.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public-api")
public class AccommodationPublicController {

    private final AccommodationService accommodationService;

    @GetMapping("/v1/accommodation")
    public BaseResponse<List<AccommodationResponse>> findAll(){
        List<AccommodationResponse> responses = accommodationService.findAllAccommodation();
        for (AccommodationResponse response : responses) {
            log.info("accommodationName= {} accommodationType= {} accommodationImage= {} introduction={} rate={} price={}",
                    response.getAccommodationName(), response.getAccommodationType(), response.getAccommodationImage(),
                    response.getIntroduction(), response.getRate(), response.getPrice());

        }
        return BaseResponse.response(responses);
    }

}
