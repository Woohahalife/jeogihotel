package com.core.miniproject.src.reservation.repository;

import com.core.miniproject.src.reservation.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            select r
            from Reservation r
            left join r.member m
            where m.id = :memberId
            """)
    List<Reservation> findAllReservation(@Param("memberId") Long id);
}
