package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>{

    @Override
    Accommodation save(Accommodation accommodation);

    @Override
    List<Accommodation> findAll();

    @Query(value = """
            SELECT a
            FROM Accommodation a
            LEFT JOIN FETCH a.roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            where a.isDeleted=false
            """
           )
    List<Accommodation> getAllAccommodation(Pageable pageable);

    @Query(value = """
            SELECT count(a)
            FROM Accommodation a
            where a.isDeleted=false
            """)
    int getCountAccommodation();

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN FETCH a.roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            where a.id = ?1 and a.isDeleted=false
            """)
    Optional<Accommodation> findByAccommodationId(Long id);

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN FETCH a.roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            where a.accommodationType = ?1 and a.isDeleted=false
            """)
    List<Accommodation> findByAccommodationType(AccommodationType accommodationType, Pageable pageable);

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN FETCH a.roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            where a.location.locationName = ?1 and a.isDeleted=false
            """)
    List<Accommodation> findByLocationType(LocationType locationType, Pageable pageable);

    @Query("""
            select a
            from Accommodation a
            LEFT JOIN FETCH a.roomId
            LEFT JOIN FETCH a.rates
            LEFT JOIN FETCH a.images
            where a.accommodationType = ?1
            and a.location.locationName = ?2 and a.isDeleted=false
            """)
    List<Accommodation> findByAccommodationTypeAndLocationType(AccommodationType aType, LocationType lType, Pageable pageable);

    @Query(""" 
       select a
       from Accommodation a
       join Room r on a.id=r.accommodationId.id
       LEFT JOIN FETCH a.rates
       LEFT JOIN FETCH a.roomId
       LEFT JOIN FETCH a.images
       where a.location.locationName=?1
       and r.fixedMember=?2 and a.isDeleted=false
       """)
    List<Accommodation> findByLocationTypeAndFixedNumber(LocationType type, int fixedMember, Pageable pageable);

}
