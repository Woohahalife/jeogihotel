package com.core.miniproject.src.board.domain.dto;

import com.core.miniproject.src.board.domain.entity.Board;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    String title;
    String content;

    public static BoardResponse toClient(Board board){
        return BoardResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
