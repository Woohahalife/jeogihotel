package com.core.miniproject.src.room.domain.entity;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.roomprice.domain.RoomPrice;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="accommodation_id")
    private Accommodation accommodationId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name="room_info")
    private String roomInfo;

    @Column(name = "room_count")
    private int roomCount;

    @Column(name="fixed_member")
    private int fixedMember;

    @Column(name="maxed_member")
    private int maxedMember;

    @OneToOne(mappedBy = "room")
    private RoomPrice roomPrice;


}
