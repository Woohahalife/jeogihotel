package com.core.miniproject.src.accommodation.controller;

import com.core.miniproject.src.accommodation.domain.dto.*;
import com.core.miniproject.src.accommodation.service.AccommodationService;
import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "숙소 생성 & 수정 & 삭제 api", description = "숙소 관련 api - 보안 접근 필요")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping(value = "/v1/accommodation/admin")
    public BaseResponse<AccommodationInsertResponse> createAccommodation(
            @RequestPart(value = "request") AccommodationInsertRequest request,
            @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
            @JwtAuthentication MemberInfo memberInfo)
    {

        AccommodationInsertResponse accommodationInsertResponse =
                accommodationService.createAccommodation(request, multipartFiles, memberInfo);

        return BaseResponse.response(accommodationInsertResponse);
    }

    @DeleteMapping("/v1/accommodation/{accommodation_id}/delete")
    public BaseResponse<BaseResponseStatus> deleteAccommodation(
            @PathVariable("accommodation_id") Long accommodationId,
            @JwtAuthentication MemberInfo memberInfo
    ){
        BaseResponseStatus responseStatus = accommodationService.deleteAccommodation(accommodationId, memberInfo);
        return BaseResponse.response(responseStatus);
    }

    @PostMapping("/v1/accommodation/{accommodation_id}/update")
    public BaseResponse<AccommodationResponse> updateAccommodation(
            @PathVariable("accommodation_id") Long accommodationId,
            @JwtAuthentication MemberInfo memberInfo,
            @RequestBody AccommodationRequest request
    ){
        AccommodationResponse accommodationResponse = accommodationService.updateAccommodation(accommodationId, request, memberInfo);
        return BaseResponse.response(accommodationResponse);
    }

    @GetMapping("/v1/accommodation/member")
    public BaseResponse<RegisteredAccommodationResponse> getAccommodationMember(
            @JwtAuthentication MemberInfo memberInfo,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "4") int size
    ) {
        if(page > 0) {
            page--;
        }

        Pageable pageable = PageRequest.of(page, size);

        RegisteredAccommodationResponse responses = accommodationService.getAccommodationMember(memberInfo, pageable);

        return BaseResponse.response(responses);
    }
}
