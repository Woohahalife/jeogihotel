package com.core.miniproject.src.room.domain.entity;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.image.domain.entity.RoomImage;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import com.core.miniproject.src.room.domain.dto.RoomRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@SQLDelete(sql = "update room set is_deleted=true where room_id=?")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="accommodation_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
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

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @Column(name = "reservation_id")
    private List<Reservation> reservations;

    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted=false;
    
    @OneToOne(mappedBy = "room", cascade = CascadeType.REMOVE)
    private RoomImage roomImage;

    public void update(RoomRequest request, RoomImage image){
        this.roomName = request.getRoomName();
        this.roomInfo = request.getRoomInfo();
        this.roomCount = request.getRoomCount();
        this.fixedMember = request.getFixedMember();
        this.maxedMember = request.getMaxedMember();
        this.roomImage = image;
        this.price = request.getPrice();
    }

}
