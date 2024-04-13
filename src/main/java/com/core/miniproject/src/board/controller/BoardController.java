package com.core.miniproject.src.board.controller;

import com.core.miniproject.src.board.domain.dto.BoardInsertRequest;
import com.core.miniproject.src.board.domain.dto.BoardInsertResponse;
import com.core.miniproject.src.board.service.BoardService;
import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/v1/register/board")
    public BaseResponse<BoardInsertResponse> registerBoard(
            @RequestBody BoardInsertRequest request,
            @JwtAuthentication MemberInfo memberInfo
    ){
        BoardInsertResponse response = boardService.saveBoard(request, memberInfo);
        return BaseResponse.response(response);
    }
}
