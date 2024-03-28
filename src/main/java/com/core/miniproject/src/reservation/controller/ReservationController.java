package com.core.miniproject.src.reservation.controller;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.reservation.model.dto.ReservationInsertRequest;
import com.core.miniproject.src.reservation.model.dto.ReservationInsertResponse;
import com.core.miniproject.src.reservation.model.dto.ReservationListResponse;
import com.core.miniproject.src.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.core.miniproject.src.common.response.BaseResponse.response;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "예약 생성 & 수정 & 삭제 api", description = "할인율 관련 api - 보안 설정 X")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/v1/reservation/insert")
    public BaseResponse<ReservationInsertResponse> registerReservation(
            @RequestBody ReservationInsertRequest request,
            @JwtAuthentication MemberInfo memberInfo)
    {

        log.info("Post Mapping - Create a new reservation - member_id : {}, member_email : {}, request : {}",
                memberInfo.getId(), memberInfo.getEmail(), request);

        return response(reservationService.registerReservation(request, memberInfo));
    }

    @GetMapping("v1/reservation")
    public BaseResponse<List<ReservationListResponse>> findAllReservaion(
            @JwtAuthentication MemberInfo memberInfo
    ) {
        log.info("Get Mapping - Select all reservation - member_id : {}, member_email : {}",
                memberInfo.getId(), memberInfo.getEmail());

        List<ReservationListResponse> reservationListResponse = reservationService.findAllReservation(memberInfo);

        return response(reservationListResponse);
    }
}

