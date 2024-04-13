package com.core.miniproject.src.board.domain.dto;

import com.core.miniproject.src.board.domain.entity.Board;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardInsertResponse {
    private String title;
    private String content;

    public static BoardInsertResponse toClient(Board board){
        return BoardInsertResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
