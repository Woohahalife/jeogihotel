package com.core.miniproject.src.board.domain.dto;

import com.core.miniproject.src.board.domain.entity.Board;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardInsertResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDate updateDate;

    public static BoardInsertResponse toClient(Board board){
        return BoardInsertResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
