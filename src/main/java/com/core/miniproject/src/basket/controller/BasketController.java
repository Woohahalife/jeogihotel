package com.core.miniproject.src.basket.controller;

import com.core.miniproject.src.basket.domain.dto.BasketCreateResponse;
import com.core.miniproject.src.basket.domain.dto.BasketCreateRequest;
import com.core.miniproject.src.basket.domain.dto.BasketDeleteRequest;
import com.core.miniproject.src.basket.domain.dto.BasketReadResponse;
import com.core.miniproject.src.basket.service.BasketService;
import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/v1/basket/{roomId}") // 장바구니 단건 등록
    public BaseResponse<BasketCreateResponse> registerBasket(
            @PathVariable(name = "roomId") Long roomId,
            @RequestBody BasketCreateRequest request,
            @JwtAuthentication MemberInfo memberInfo)
    {
        BasketCreateResponse response = basketService.registerBasket(roomId, request, memberInfo);

        return BaseResponse.response(response);
    }

    @GetMapping("/v1/basket/all")
    public BaseResponse<List<BasketReadResponse>> readAllBasket(@JwtAuthentication MemberInfo memberInfo) {

        List<BasketReadResponse> responses = basketService.readAllBasket(memberInfo);

        return BaseResponse.response(responses);
    }

    @DeleteMapping("/v1/basket/delete/detail") // 선택삭제(1개~전체 삭제까지 함께 가능)
    public BaseResponse<Integer> deleteSelectedBasket(
            @RequestBody BasketDeleteRequest request,
            @JwtAuthentication MemberInfo memberInfo) {

        Integer result = basketService.deleteSelectedBasket(request, memberInfo);

        return BaseResponse.response(result);
    }
}
