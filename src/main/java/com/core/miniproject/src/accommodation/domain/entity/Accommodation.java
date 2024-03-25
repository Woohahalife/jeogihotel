package com.core.miniproject.src.accommodation.domain.entity;

import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.room.domain.entity.Room;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    //location_id 참조 관계 설정
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

    //room_id 참조 관계 설정
    @OneToMany(mappedBy = "accommodationId")
    @Column(name = "room_id")
    private List<Room> roomId;

    @Column(name="introduction")
    private String introduction;
    @Column(name="accommodation_name")
    private String accommodationName;

    @Column(name="accommodation_type")
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    @Column(name="accommodation_image")
    private String accommodationImage;

    //해당 숙소의 별점의 평균을 출력하는 쿼리
    @Formula("select avg(r.rate) from rate r where r.accommodation_id = accommodation_id")
    private double rate;

    // 객실 가격 중 최소값을 반환하는 쿼리
    @Formula("select min(rp.price) from room_price rp join room r on rp.room_id = r.room_id where r.accommodation_id = accommodation_id")
    private int price;

    @OneToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

}
