package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.AccommodationDto;
import com.core.miniproject.src.accommodation.domain.AccommodationType;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaAccommodationRepository extends JpaRepository<AccommodationDto, Long>{

    @Override
    AccommodationDto save(AccommodationDto accommodationDto);

    @Override
    List<AccommodationDto> findAll();

    Optional<AccommodationDto> findByAccommodationType(AccommodationType accommodationType);
}
