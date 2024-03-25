package com.core.miniproject.src.location.repository;


import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.location.domain.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    //for 테스트, 등록 기능 구현 시 실제 구현 필요,
    @Override
    Location save(Location location);

    @Query("""
            select lo
            from location lo
            where lo.locationName =:location_Name
            """)
    Optional<Location> findLocationByType(@Param("location_Name") LocationType locationName);

    //TODO:accommodation과 묶어서 조회 기능 구현
}
