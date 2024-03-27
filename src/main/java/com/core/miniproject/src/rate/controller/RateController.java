package com.core.miniproject.src.rate.controller;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.rate.domain.dto.RateInsertRequest;
import com.core.miniproject.src.rate.domain.dto.RateInsertResponse;
import com.core.miniproject.src.rate.service.RateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "할인율 생성 & 수정 & 삭제 api", description = "할인율 관련 api - 보안 설정 필요")
public class RateController {

    private final RateService rateService;


    @PostMapping("/v1/{accommodationId}/rate/create")
    public BaseResponse<RateInsertResponse> createRate(
            @PathVariable("accommodationId") Long accommodationId,
            @RequestBody RateInsertRequest request,
            @JwtAuthentication MemberInfo memberInfo
    ){
        log.info("Post Mapping - Create a new rate with accommodationId : {}," +
                        " Request Details : {}",
                accommodationId, request);
        RateInsertResponse response = rateService.createRate(accommodationId,request, memberInfo);
        return BaseResponse.response(response);
    }
}
