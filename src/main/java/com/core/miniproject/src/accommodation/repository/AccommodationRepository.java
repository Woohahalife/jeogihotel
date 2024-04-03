package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import org.springframework.cglib.core.Local;
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
            SELECT DISTINCT a
            FROM Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN roomId.reservations r
            WHERE a.isDeleted=false
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR (r.checkIn > :checkOut OR r.checkOut < :checkIn))
            """)
    List<Accommodation> getAllAccommodation(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkInOut, Pageable pageable);

    @Query(value = """
            SELECT COUNT(DISTINCT a)
            FROM Accommodation a
            LEFT JOIN a.roomId roomId
            LEFT JOIN a.rates
            LEFT JOIN a.images
            LEFT JOIN roomId.reservations r
            WHERE a.isDeleted=false
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR (r.checkIn > :checkOut OR r.checkOut < :checkIn))
            """)
    int getCountAccommodation(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkInOut);

    @Query("""
            select DISTINCT a
            from Accommodation a
            LEFT JOIN FETCH a.roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            where a.id = ?1 and a.isDeleted=false
            """)
    Optional<Accommodation> findByAccommodationId(Long id);

    @Query("""
            select DISTINCT a
            from Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN roomId.reservations r
            where a.accommodationType =:accommodationType and a.isDeleted=false
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR (r.checkIn > :checkOut OR r.checkOut < :checkIn))
            """)
    List<Accommodation> findByAccommodationType(@Param("accommodationType") AccommodationType accommodationType, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkInOut, Pageable pageable);

    @Query("""
            select DISTINCT a
            from Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN roomId.reservations r
            where a.location.locationName =:locationType and a.isDeleted=false
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR (r.checkIn > :checkOut OR r.checkOut < :checkIn))
            """)
    List<Accommodation> findByLocationType(@Param("locationType") LocationType locationType, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkInOut, Pageable pageable);

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN roomId.reservations r
            where a.accommodationType =:aType
            and a.location.locationName =:lType and a.isDeleted=false
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR (r.checkIn > :checkOut OR r.checkOut < :checkIn))
            """)
    List<Accommodation> findByAccommodationTypeAndLocationType(@Param("aType")AccommodationType aType, @Param("lType")LocationType lType, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkInOut, Pageable pageable);

    @Query(""" 
            select a
            from Accommodation a
            join Room r on a.id=r.accommodationId.id
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.images
            LEFT JOIN roomId.reservations ra
            where a.location.locationName=:type
            and r.fixedMember=:fixedMember and a.isDeleted=false
            AND (ra is null OR ra.isVisited <> 'VISIT_DATE')
            AND (ra is null OR ra.isVisited <> 'VISITED')
            AND (ra is null OR (ra.checkIn > :checkOut OR ra.checkOut < :checkIn))
            """)
    List<Accommodation> findByLocationTypeAndFixedNumber(@Param("type") LocationType type, @Param("fixedMember") int fixedMember,  @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkInOut, Pageable pageable);

    @Query("""
            select DISTINCT a
            from Accommodation a
            LEFT JOIN FETCH a.roomId roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            LEFT JOIN roomId.reservations r
            where a.id =:id and a.isDeleted=false
            AND (r is null OR r.isVisited <> 'VISIT_DATE')
            AND (r is null OR r.isVisited <> 'VISITED')
            AND (r is null OR (r.checkIn > :checkOut OR r.checkOut < :checkIn))
            """)
    Optional<Accommodation> accommodationDetailInfo(@Param("id") Long id, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkInOut);

}
