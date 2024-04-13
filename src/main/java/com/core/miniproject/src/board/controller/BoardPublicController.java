package com.core.miniproject.src.board.controller;

import com.core.miniproject.src.board.domain.dto.BoardInsertResponse;
import com.core.miniproject.src.board.domain.dto.BoardResponse;
import com.core.miniproject.src.board.domain.entity.Board;
import com.core.miniproject.src.board.service.BoardService;
import com.core.miniproject.src.common.response.BaseResponse;
import com.core.miniproject.src.common.security.JwtAuthentication;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public-api")
@Tag(name = "공지사항 생성 & 수정 & 삭제 api", description = "공지사항 관련 api - 보안 접근 필요")
public class BoardPublicController {

    private final BoardService boardService;

    @GetMapping("/v1/board")
    public BaseResponse<List<BoardResponse>> findAllBoard(){
        List<BoardResponse> responses = boardService.findAllBoard();
        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/board/search/title/{title}")
    public BaseResponse<List<BoardResponse>> searchByTitle(
            @PathVariable("title") String title
    ){
        List<BoardResponse> responses = boardService.searchByTitle(title);
        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/board/search/content/{content}")
    public BaseResponse<List<BoardResponse>> searchByContent(
            @PathVariable("content") String content
    ){
        List<BoardResponse> responses = boardService.searchByContent(content);
        return BaseResponse.response(responses);
    }

}
