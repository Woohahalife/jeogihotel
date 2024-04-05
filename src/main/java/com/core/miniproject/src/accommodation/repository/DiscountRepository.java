package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Override
    Discount save(Discount discount);

    @Query("""
            select d
            from Discount d
            where d.discountRate =:discount_rate
           """)
    Optional<Discount> findDiscountByRate(@Param("discount_rate") Double rate);

}
