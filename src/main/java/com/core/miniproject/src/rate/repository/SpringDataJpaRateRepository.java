package com.core.miniproject.src.rate.repository;

import com.core.miniproject.src.rate.domain.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaRateRepository extends JpaRepository<Rate, Long> {
    @Override
    Rate save(Rate rate);
}
