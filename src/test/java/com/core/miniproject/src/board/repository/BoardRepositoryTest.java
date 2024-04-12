package com.core.miniproject.src.board.repository;

import com.core.miniproject.src.board.domain.entity.Board;
import com.core.miniproject.src.member.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void 공지사항생성_성공(){
        Board board = Board.builder()
                .content("테스트")
                .member(Arrays.asList(Member.builder()
                        .id(1L)
                        .build()))
                .updateDate(LocalDate.now())
                .deleteDate(null)
                .build();

        Board newBoard = boardRepository.save(board);
        Assertions.assertThat(newBoard.getContent()).isEqualTo(board.getContent());
    }

    @Test
    void 공지사항전체조회_성공(){
        Board board1 = Board.builder()
                .content("테스트1")
                .member(Arrays.asList(Member.builder()
                        .id(1L)
                        .build()))
                .updateDate(LocalDate.now())
                .deleteDate(null)
                .build();

        Board board2 = Board.builder()
                .content("테스트2")
                .member(Arrays.asList(Member.builder()
                        .id(1L)
                        .build()))
                .is_deleted(true)
                .updateDate(LocalDate.of(2024, 4, 10))
                .deleteDate(LocalDate.now())
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);
        List<Board> boards = boardRepository.findAll();

        Assertions.assertThat(boards.size()).isEqualTo(1);



    }
}