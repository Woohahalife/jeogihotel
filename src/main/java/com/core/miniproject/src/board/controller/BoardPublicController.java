package com.core.miniproject.src.board.controller;


import com.core.miniproject.src.board.domain.dto.BoardResponse;
import com.core.miniproject.src.board.service.BoardService;
import com.core.miniproject.src.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public-api")
@Tag(name = "공지사항 생성 & 수정 & 삭제 api", description = "공지사항 관련 api - 보안 접근 필요")
public class BoardPublicController {

    private final BoardService boardService;

    @GetMapping("/v1/board")
    public BaseResponse<List<BoardResponse>> findAllBoard(
//            @RequestParam(name="page", defaultValue = "0") int page,
//            @RequestParam(name="size", defaultValue = "10") int size
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        List<BoardResponse> responses = boardService.findAllBoard(pageable);
        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/board/search/title/{title}")
    public BaseResponse<List<BoardResponse>> searchByTitle(
            @PathVariable("title") String title,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @Parameter(hidden = true) Pageable pageable
    ){
        List<BoardResponse> responses = boardService.searchByTitle(title, pageable);
        return BaseResponse.response(responses);
    }

    @GetMapping("/v1/board/search/content/{content}")
    public BaseResponse<List<BoardResponse>> searchByContent(
            @PathVariable("content") String content,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @Parameter(hidden = true) Pageable pageable
    ){
        List<BoardResponse> responses = boardService.searchByContent(content, pageable);
        return BaseResponse.response(responses);
    }

}
