package com.core.miniproject.src.location.repository;


import com.core.miniproject.src.location.domain.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    //for 테스트, 등록 기능 구현 시 실제 구현 필요,
    @Override
    Location save(Location location);

    //TODO:accommodation과 묶어서 조회 기능 구현
}
