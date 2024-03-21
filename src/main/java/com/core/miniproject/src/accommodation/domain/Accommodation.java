package com.core.miniproject.src.accommodation.domain;

import com.core.miniproject.src.room.domain.Room;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "accommodation")
@Data
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    //TODO: 참조 관계에 있는 컬럼 아직 설정하지 않았음. price
    //room_id 참조 관계 설정
    @OneToMany(mappedBy = "accommodation")
    @Column(name = "room_id")
    private List<Room> roomId;

//    //location_id 참조 관계 설정
//    @OneToOne
//    @JoinColumn(name = "location_id")
//    private Location locationId;
//
//    //rate_id 참조 관계 설정
//    @OneToMany
//    @JoinColumn(name = "rate_id")
//    private List<Rate> rateId;

    @Column(name="introduction")
    private String introduction;
    @Column(name="accommodation_name")
    private String accommodationName;


    @Column(name="accommodation_type")
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    @Column(name="accommodation_image")
    private String accommodationImage;
}
