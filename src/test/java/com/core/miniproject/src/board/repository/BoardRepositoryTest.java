package com.core.miniproject.src.board.repository;

import com.core.miniproject.src.board.domain.entity.Board;
import com.core.miniproject.src.member.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void 공지사항생성_성공(){
        Board board = Board.builder()
                .content("테스트")
                .member(Member.builder()
                        .id(1L)
                        .build())
                .updateDate(LocalDate.now())
                .deleteDate(null)
                .build();

        Board newBoard = boardRepository.save(board);
        Assertions.assertThat(newBoard.getContent()).isEqualTo(board.getContent());
    }
}