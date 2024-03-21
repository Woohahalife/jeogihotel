package com.core.miniproject.src.room.domain;

import com.core.miniproject.src.accommodation.domain.Accommodation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="accommodation_id")
    private Accommodation accommodation;

    @Column(name="room_info")
    private String roomInfo;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_count")
    private int roomCount;

    @Column(name="fixed_member")
    private int fixedMember;

    @Column(name="maxed_member")
    private int maxedMember;


}
