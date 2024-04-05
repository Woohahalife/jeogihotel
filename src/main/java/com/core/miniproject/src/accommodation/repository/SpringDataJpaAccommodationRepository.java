package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaAccommodationRepository extends JpaRepository<Accommodation, Long>{

    @Override
    Accommodation save(Accommodation accommodation);

    @Override
    List<Accommodation> findAll();

    Optional<Accommodation> findByAccommodationType(AccommodationType accommodationType);
}
