package com.core.miniproject.src.roomprice.domain;

import com.core.miniproject.src.room.domain.entity.Room;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RoomPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_price_id")
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Room room;

    @Column(name = "price")
    private int price;


}
