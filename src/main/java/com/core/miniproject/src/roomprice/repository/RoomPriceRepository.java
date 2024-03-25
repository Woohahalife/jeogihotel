package com.core.miniproject.src.roomprice.repository;

import com.core.miniproject.src.roomprice.domain.RoomPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomPriceRepository extends JpaRepository<RoomPrice, Long> {

    @Override
    RoomPrice save(RoomPrice roomPrice);

    @Override
    List<RoomPrice> findAll();

}
