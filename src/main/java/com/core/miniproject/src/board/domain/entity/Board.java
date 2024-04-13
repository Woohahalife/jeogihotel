package com.core.miniproject.src.board.domain.entity;

import com.core.miniproject.src.board.domain.dto.BoardInsertRequest;
import com.core.miniproject.src.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@SQLDelete(sql = "update Board set is_deleted=true where board_id=?")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name="title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "is_deleted")
    private boolean is_deleted=false;
    @Column(name = "update_date")
    private LocalDate updateDate;

    public void update(BoardInsertRequest request){
        this.title = request.getTitle();
        this.content = request.getContent();
        this.updateDate = LocalDate.now();
    }
}
