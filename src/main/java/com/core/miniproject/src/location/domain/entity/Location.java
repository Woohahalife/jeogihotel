package com.core.miniproject.src.location.domain.entity;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<Accommodation> accommodation;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_name", unique = true)
    private LocationType locationName;
}
