package com.core.miniproject.src.board.service;

import com.core.miniproject.src.board.domain.dto.BoardAllResponse;
import com.core.miniproject.src.board.domain.dto.BoardInsertRequest;
import com.core.miniproject.src.board.domain.dto.BoardInsertResponse;
import com.core.miniproject.src.board.domain.dto.BoardResponse;
import com.core.miniproject.src.board.domain.entity.Board;
import com.core.miniproject.src.board.repository.BoardRepository;
import com.core.miniproject.src.common.entity.BaseEntity;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.core.miniproject.src.common.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class BoardService extends BaseEntity {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BoardInsertResponse saveBoard(BoardInsertRequest request, MemberInfo memberInfo){

        Member member = emailValidate(memberInfo);
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .updateDate(LocalDate.now())
                .build();

        return BoardInsertResponse.toClient(boardRepository.save(board));
    }

    @Transactional
    public BoardAllResponse findAllBoard(Pageable pageable){

        Page<Board> boards = boardRepository.findAllBoard(pageable);
        List<BoardResponse> boardResponses = boards.stream()
                .map(BoardResponse::toClient)
                .toList();

        return BoardAllResponse.toClient(boardResponses, (int) boards.getTotalElements());
    }

    @Transactional
    public BoardAllResponse searchByTitle(String title, Pageable pageable){
        Page<Board> boards = boardRepository.findByTitleContains(title, pageable);

        List<BoardResponse> boardResponses = boards.stream()
                .map(BoardResponse::toClient)
                .toList();

        return BoardAllResponse.toClient(boardResponses, (int) boards.getTotalElements());
    }
    @Transactional
    public BoardAllResponse searchByContent(String content, Pageable pageable){
        Page<Board> boards = boardRepository.findByContentContains(content, pageable);
        List<BoardResponse> boardResponses = boards.stream()
                .map(BoardResponse::toClient)
                .toList();

        return BoardAllResponse.toClient(boardResponses, (int) boards.getTotalElements());
    }

    @Transactional
    public BoardInsertResponse updateBoard(Long boardId, BoardInsertRequest request, MemberInfo memberInfo){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new BaseException(BOARD_NOT_FOUND));
        board.update(request);
        Board newBoard = boardRepository.save(board);
        return BoardInsertResponse.toClient(newBoard);
    }

    @Transactional
    public BaseResponseStatus deleteBoard(Long boardId, MemberInfo memberInfo){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new BaseException(BOARD_NOT_FOUND));

        try {
            boardRepository.deleteById(board.getId());
            return BaseResponseStatus.DELETE_SUCCESS;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DELETE_FAIL);
        }
    }

    private Member emailValidate(MemberInfo memberInfo) {
        Member member = memberRepository.findByMemberEmail(memberInfo.getEmail())
                .orElseThrow(() -> new BaseException(EMAIL_NOT_FOUND));

        if (!memberInfo.getEmail().equals(member.getEmail())) {
            throw new BaseException(EMAIL_IS_NOT_VALIDATE);
        }

        return member;
    }
}
