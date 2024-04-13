package com.core.miniproject.src.board.service;

import com.core.miniproject.src.board.domain.dto.BoardInsertRequest;
import com.core.miniproject.src.board.domain.dto.BoardInsertResponse;
import com.core.miniproject.src.board.domain.entity.Board;
import com.core.miniproject.src.board.repository.BoardRepository;
import com.core.miniproject.src.common.entity.BaseEntity;
import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.security.principal.MemberInfo;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.core.miniproject.src.common.response.BaseResponseStatus.EMAIL_IS_NOT_VALIDATE;
import static com.core.miniproject.src.common.response.BaseResponseStatus.EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BoardService extends BaseEntity {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

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


    private Member emailValidate(MemberInfo memberInfo) {
        Member member = memberRepository.findByMemberEmail(memberInfo.getEmail())
                .orElseThrow(() -> new BaseException(EMAIL_NOT_FOUND));

        if (!memberInfo.getEmail().equals(member.getEmail())) {
            throw new BaseException(EMAIL_IS_NOT_VALIDATE);
        }

        return member;
    }
}
