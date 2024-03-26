package com.core.miniproject.src.room.repository;

import com.core.miniproject.src.room.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Override
    Room save(Room room);

    @Query("select r from Room r where r.accommodationId.id = ?1")
    List<Room> findAllByAccommodationId(Long accommodationId);

    
}
