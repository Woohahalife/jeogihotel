package com.core.miniproject.src.room.controller;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.room.domain.dto.RoomResponse;
import com.core.miniproject.src.room.service.RoomService;
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
public class RoomPublicController {

    private final RoomService roomService;

    @GetMapping("/v1/accommodation/{accommodationId}")
    public BaseResponse<List<RoomResponse>> findAllRoomByAccommodationId(@PathVariable("accommodationId") int accommodationId){

        List<RoomResponse> roomResponses = roomService.findAllRoomByAccommodationId((long)accommodationId);

        return BaseResponse.response(roomResponses);
    }
}
