package com.core.miniproject.src.accommodation.repository;

import com.core.miniproject.src.accommodation.domain.entity.Accommodation;
import com.core.miniproject.src.accommodation.domain.entity.AccommodationType;
import com.core.miniproject.src.location.domain.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>{

    @Override
    Accommodation save(Accommodation accommodation);

    @Override
    List<Accommodation> findAll();

    List<Accommodation> findByAccommodationType(AccommodationType accommodationType);

    @Query("select a from Accommodation a where a.locationId.locationName = ?1 ")
    List<Accommodation> findByLocationType(LocationType locationType);

    @Query("select a from Accommodation a where a.accommodationType = ?1 and a.locationId.locationName = ?2")
    List<Accommodation> findByAccommodationTypeAndLocationType(AccommodationType aType, LocationType lType);

    @Query(""" 
       select a 
       from Accommodation a join Room r on a.id=r.accommodationId.id
       where a.locationId.locationName=?1 
       and r.fixedMember=?2 
       """)
    List<Accommodation> findByLocationTypeAndFixedNumber(LocationType type, int fixedMember);
}
