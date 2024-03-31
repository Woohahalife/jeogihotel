package com.core.miniproject.src.accommodation.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "discount")
    private List<Accommodation> accommodation = new ArrayList<>();

    @Column(name = "discount_rate", unique = true)
    @Builder.Default
    private Double discountRate = 0.0;

//    public Discount(List<Accommodation> accommodations, Double discountRate){
//        this.accommodation = accommodations;
//        this.discountRate=discountRate;
//    }
}
