package com.core.miniproject.src.reservation.model.entity;

import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.room.domain.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(indexes = @Index(name = "IX_reservation_member_id", columnList = "member_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "address")
    private String address;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    private int price;

    @Column(name = "fixed_number")
    private int fixedNumber;

    @Column(name = "maxed_number")
    private int maxedNumber;

    @Column(name = "is_visited")
    @Enumerated(EnumType.STRING)
    private IsVisited isVisited;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) // 테스트 및 조작 유연성을 위한 외래키 제약조건 해제
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;
}
