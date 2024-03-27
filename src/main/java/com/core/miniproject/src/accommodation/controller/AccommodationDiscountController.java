package com.core.miniproject.src.accommodation.controller;

import com.core.miniproject.src.accommodation.domain.dto.DiscountInsertRequest;
import com.core.miniproject.src.accommodation.domain.dto.DiscountInsertResponse;
import com.core.miniproject.src.accommodation.repository.DiscountRepository;
import com.core.miniproject.src.accommodation.service.AccommodationDiscountService;
import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.core.miniproject.src.common.response.BaseResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "할인율 생성 & 수정 & 삭제 api", description = "숙소-할인율 관련 api - 보안 접근 필요")
public class AccommodationDiscountController {

    private final AccommodationDiscountService discountService;

    @PostMapping("/v1/discount")
    public BaseResponse<DiscountInsertResponse> createDiscount(
            @RequestBody DiscountInsertRequest request,
            @JwtAuthentication MemberInfo memberInfo
            )
    {
        DiscountInsertResponse discountResponse =
                discountService.createDiscount(request);

        return response(discountResponse);
    }
}
