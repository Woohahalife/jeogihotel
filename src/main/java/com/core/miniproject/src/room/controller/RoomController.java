package com.core.miniproject.src.room.controller;

import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.room.domain.dto.RoomInsertRequest;
import com.core.miniproject.src.room.domain.dto.RoomInsertResponse;
import com.core.miniproject.src.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.core.miniproject.src.common.response.BaseResponse.response;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/v1/{accommodationId}/room")
    public BaseResponse<RoomInsertResponse> createRoom(
            @PathVariable("accommodationId") Long accommodationId,
            @RequestBody RoomInsertRequest request,
            @JwtAuthentication MemberInfo memberInfo)
    {
        log.info("Post Mapping - Create a new room with accommodationId : {}," +
                 " Request Details : {}",
                accommodationId, request);

        RoomInsertResponse roomResponse = roomService.createRoom(accommodationId, request, memberInfo);

        return response(roomResponse);
    }
}
