package com.core.miniproject.src.accommodation.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "accommodation")
@Data
public class AccommodationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    //TODO: 참조 관계에 있는 컬럼 아직 설정하지 않았음. room_id, location_id, rate_id, price
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
