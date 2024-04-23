package com.core.miniproject.src.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BoardAllResponse {

    private Integer boardCount;
    private List<BoardResponse> boards;

    public static BoardAllResponse toClient(List<BoardResponse> boards, Integer count){
        return BoardAllResponse.builder()
                .boardCount(count)
                .boards(boards)
                .build();
    }
}
