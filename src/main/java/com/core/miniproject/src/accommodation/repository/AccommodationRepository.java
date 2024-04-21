package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Override
    Accommodation save(Accommodation accommodation);

    @Override
    List<Accommodation> findAll();

    @Query(value = """
            SELECT DISTINCT a.*
            FROM Accommodation a
            LEFT JOIN Room r ON a.accommodation_id = r.accommodation_id
            LEFT JOIN Rate rate ON a.accommodation_id = rate.accommodation_id
            LEFT JOIN AccommodationImage ai ON a.accommodation_id = ai.accommodation_id
            LEFT JOIN Discount d ON a.discount_id = d.discount_id
            LEFT JOIN Reservation res ON r.room_id = res.room_id
            WHERE a.is_deleted = false
            AND (res.room_id IS NULL OR res.is_visited <> 'VISIT_DATE')
            AND (res.room_id IS NULL OR res.is_visited <> 'VISITED')
            AND (res.room_id IS NULL OR ((res.check_in >= :checkIn AND res.check_out <= :checkOut) OR (res.check_in < :checkIn AND res.check_out > :checkOut)))
            AND (res.room_id IS NULL OR (res.check_out <= :checkIn OR res.check_in >= :checkOut))
            AND (res.room_id IS NULL OR (res.check_out <= :checkOut OR res.check_in >= :checkOut));
            """, nativeQuery = true)
    List<Accommodation> getAllAccommodation(@Param("checkIn") LocalDate checkIn,
                                            @Param("checkOut") LocalDate checkInOut);

    @Query(value = """
            SELECT DISTINCT a
            FROM Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN FETCH a.discount
            LEFT JOIN roomId.reservations r
            WHERE a.isDeleted=false
            AND a.accommodationType = :accommodationType
            AND a.location.locationName = :locationType
            AND roomId.fixedMember >= :personal
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR not ((r.checkIn >= :checkIn and r.checkOut <= :checkOut) or (r.checkIn < :checkIn and r.checkOut > :checkOut)))
            AND (r is null OR (r.checkOut <= :checkIn OR r.checkIn >= :checkOut))
            AND (r is null OR (r.checkOut <= :checkOut OR r.checkIn >= :checkOut))
            ORDER BY roomId.price ASC
            """)
    List<Accommodation> findAllAccommodation(@Param("checkIn") LocalDate checkIn,
                                             @Param("checkOut") LocalDate checkInOut,
                                             @Param("locationType") LocationType locationType,
                                             @Param("accommodationType") AccommodationType accommodationType,
                                             @Param("personal") Integer personal,
                                             Pageable pageable);

    @Query(value = """
            SELECT COUNT (DISTINCT a)
            FROM Accommodation a
            LEFT JOIN a.roomId roomId
            LEFT JOIN a.rates
            LEFT JOIN a.images
            LEFT JOIN a.discount
            LEFT JOIN roomId.reservations r
            WHERE a.isDeleted=false
            AND a.accommodationType = :accommodationType
            AND a.location.locationName = :locationType
            AND roomId.fixedMember >= :personal
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR not ((r.checkIn >= :checkIn and r.checkOut <= :checkOut) or (r.checkIn < :checkIn and r.checkOut > :checkOut)))
            AND (r is null OR (r.checkOut <= :checkIn OR r.checkIn >= :checkOut))
            AND (r is null OR (r.checkOut <= :checkOut OR r.checkIn >= :checkOut))
            """)
    int getCountAccommodation(@Param("checkIn") LocalDate checkIn,
                              @Param("checkOut") LocalDate checkInOut,
                              @Param("locationType") LocationType locationType,
                              @Param("accommodationType") AccommodationType accommodationType,
                              @Param("personal") Integer personal);

    @Query("""
            select DISTINCT a
            from Accommodation a
            LEFT JOIN FETCH a.roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN FETCH a.discount
            where a.id = ?1 and a.isDeleted=false
            """)
    Optional<Accommodation> findByAccommodationId(Long id);

    @Query("""
            SELECT DISTINCT a
            FROM Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN FETCH a.discount
            LEFT JOIN roomId.reservations r
            WHERE a.id =:id AND a.isDeleted=false
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR not ((r.checkIn >= :checkIn and r.checkOut <= :checkOut) or (r.checkIn < :checkIn and r.checkOut > :checkOut)))
            AND (r is null OR (r.checkOut <= :checkIn OR r.checkIn >= :checkOut))
            AND (r is null OR (r.checkOut <= :checkOut OR r.checkIn >= :checkOut))
            ORDER BY roomId.price ASC
            """)
    Optional<Accommodation> accommodationDetailInfo(@Param("id") Long id,
                                                    @Param("checkIn") LocalDate checkIn,
                                                    @Param("checkOut") LocalDate checkInOut);

    @Query(value = """
            SELECT DISTINCT a
            FROM Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN FETCH a.discount
            WHERE a.memberId =:memberId AND a.isDeleted=false
            """
    )
    Page<Accommodation> accommodationRegisteredMember(@Param("memberId") Long memberId, Pageable pageable);
}
