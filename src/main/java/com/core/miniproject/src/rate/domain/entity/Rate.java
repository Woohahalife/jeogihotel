package com.core.miniproject.src.rate.domain.entity;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@SQLDelete(sql = "update rate set is_deleted=true where rate_id=?")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Accommodation accommodation;

    @Column(name = "rate")
    private double rate;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean isDeleted=false;

}
