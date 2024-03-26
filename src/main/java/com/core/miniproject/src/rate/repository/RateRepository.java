package com.core.miniproject.src.rate.repository;

import com.core.miniproject.src.rate.domain.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    @Override
    Rate save(Rate rate);

    @Query("select r from Rate r where r.accommodation.id=?1 ")
    List<Rate> findByAccommodationId(Long id);
}
