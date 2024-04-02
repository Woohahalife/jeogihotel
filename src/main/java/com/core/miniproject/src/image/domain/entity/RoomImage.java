package com.core.miniproject.src.image.domain.entity;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.room.domain.entity.Room;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_image_id")
    private Long id;

    @Column(name = "r_image_path")
    private String imagePath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    public void assignRoom(Room room) {
        this.room = room;
    }


    public RoomImage(String imagePath, Room room){
        this.imagePath = imagePath;
        this.room = room;
    }

    public void updateImagePath(String imagePath) {
        this.imagePath=imagePath;
    }
}
