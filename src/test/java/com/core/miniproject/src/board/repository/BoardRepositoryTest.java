package com.core.miniproject.src.board.repository;

import com.core.miniproject.src.board.domain.entity.Board;
import com.core.miniproject.src.member.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.List;

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
                .build();

        Board newBoard = boardRepository.save(board);
        Assertions.assertThat(newBoard.getContent()).isEqualTo(board.getContent());
    }

    @Test
    void 공지사항전체조회_성공(){
        Board board1 = Board.builder()
                .content("테스트1")
                .member((Member.builder()
                        .id(1L)
                        .build()))
                .updateDate(LocalDate.now())
                .build();

        Board board2 = Board.builder()
                .content("테스트2")
                .member((Member.builder()
                        .id(1L)
                        .build()))
                .is_deleted(true)
                .updateDate(LocalDate.of(2024, 4, 10))
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);
        List<Board> boards = boardRepository.findAll();

        Assertions.assertThat(boards.size()).isEqualTo(1);
    }

    @Test
    void 제목으로조회_성공(){
        Member member = Member.builder()
                .id(1L)
                .build();

        Board board1 = Board.builder()
                .title("테스트제목1")
                .content("테스트1")
                .member(member)
                .updateDate(LocalDate.now())
                .build();

        Board board2 = Board.builder()
                .title("테스트제목2")
                .content("테스트2")
                .member(member)
                .is_deleted(false)
                .updateDate(LocalDate.of(2024, 4, 10))
                .build();
        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> boards = boardRepository.findByTitleContains("목");
        for (Board board : boards) {
            System.out.println("board.getTitle() = " + board.getTitle());
        }

        Assertions.assertThat(boards.size()).isEqualTo(2);

    }

    @Test
    void 내용으로조회_성공(){
        Member member = Member.builder()
                .id(1L)
                .build();

        Board board1 = Board.builder()
                .title("테스트제목1")
                .content("테스트1")
                .member(member)
                .updateDate(LocalDate.now())
                .build();

        Board board2 = Board.builder()
                .title("테스트제목2")
                .content("테스트2")
                .member(member)
                .is_deleted(false)
                .updateDate(LocalDate.of(2024, 4, 10))
                .build();
        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> boards = boardRepository.findByContentContains("테");
        for (Board board : boards) {
            System.out.println("board.getTitle() = " + board.getContent());
        }

        Assertions.assertThat(boards.size()).isEqualTo(2);
    }
}