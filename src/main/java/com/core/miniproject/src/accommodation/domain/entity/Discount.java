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
    private double discountRate = 0.0;
}
