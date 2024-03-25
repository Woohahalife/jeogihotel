package com.core.miniproject.src.location.controller;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.location.domain.dto.LocationRequest;
import com.core.miniproject.src.location.domain.dto.LocationResponse;
import com.core.miniproject.src.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.core.miniproject.src.common.response.BaseResponse.response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/v1/location")
    public BaseResponse<LocationResponse> createLocation(
            @RequestBody LocationRequest request,
            @JwtAuthentication MemberInfo memberInfo
            )
    {
        LocationResponse locationResponse = locationService.createLocation(request);

        return response(locationResponse);
    }
}
