package com.core.miniproject.src.image.domain.entity;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AccommodationImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_image_id")
    private Long id;

    @Column(name = "a_image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Accommodation accommodation;

    public void assignAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
