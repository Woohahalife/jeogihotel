package com.core.miniproject.src.board.controller;

import com.core.miniproject.src.board.domain.dto.BoardInsertRequest;
import com.core.miniproject.src.board.domain.dto.BoardInsertResponse;
import com.core.miniproject.src.board.service.BoardService;
import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "공지사항 생성 & 수정 & 삭제 api", description = "공지사항 관련 api - 보안 접근 필요")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/v1/board/register")
    public BaseResponse<BoardInsertResponse> registerBoard(
            @RequestBody BoardInsertRequest request,
            @JwtAuthentication MemberInfo memberInfo
    ){
        BoardInsertResponse response = boardService.saveBoard(request, memberInfo);
        return BaseResponse.response(response);
    }
}
