package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>{

    @Override
    Accommodation save(Accommodation accommodation);

    @Override
    List<Accommodation> findAll();

    @Query("""
            SELECT a
            FROM Accommodation a
            LEFT JOIN a.rates
            LEFT JOIN a.images
            """)
    List<Accommodation> getAllAccommodation();

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN a.rates
            where a.id = ?1
            """)
    Optional<Accommodation> findByAccommodationId(Long id);

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN FETCH a.rates
            where a.accommodationType = ?1
            """)
    List<Accommodation> findByAccommodationType(AccommodationType accommodationType);

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN FETCH a.rates
            where a.location.locationName = ?1
            """)
    List<Accommodation> findByLocationType(LocationType locationType);

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN a.rates
            where a.accommodationType = ?1
            and a.location.locationName = ?2
            """)
    List<Accommodation> findByAccommodationTypeAndLocationType(AccommodationType aType, LocationType lType);

    @Query(""" 
       select a
       from Accommodation a 
       join Room r on a.id=r.accommodationId.id
       LEFT JOIN FETCH a.rates
       where a.location.locationName=?1
       and r.fixedMember=?2
       """)
    List<Accommodation> findByLocationTypeAndFixedNumber(LocationType type, int fixedMember);

}
