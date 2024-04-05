package com.core.miniproject.src.room.domain.entity;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.image.domain.entity.RoomImage;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import com.core.miniproject.src.room.domain.dto.RoomRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@SQLDelete(sql = "update Room set is_deleted=true where room_id=?")
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

    @Column(name="fixed_member")
    private int fixedMember;

    @Column(name="maxed_member")
    private int maxedMember;

    @Column(name = "price")
    private Integer price;

    @Builder.Default
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @Column(name = "reservation_id")
    private Set<Reservation> reservations = new HashSet<>();

    @Column(name = "is_deleted")
    private boolean isDeleted=false;
    
    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL)
    private RoomImage roomImage;

    public void update(RoomRequest request, RoomImage image){
        this.roomName = request.getRoomName();
        this.roomInfo = request.getRoomInfo();
        this.fixedMember = request.getFixedMember();
        this.maxedMember = request.getMaxedMember();
        this.roomImage = image;
        this.price = request.getPrice();
    }

}
