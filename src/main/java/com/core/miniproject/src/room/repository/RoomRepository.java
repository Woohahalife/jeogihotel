package com.core.miniproject.src.room.repository;

import com.core.miniproject.src.room.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Override
    Room save(Room room);

    @Query("select r from Room r where r.accommodationId.id = ?1 and r.accommodationId.isDeleted=false and r.isDeleted=false")
    List<Room> findAllByAccommodationId(Long accommodationId);

    @Query("""
    select r from Room r
    where r.accommodationId.id = ?1 and r.id=?2
    and r.accommodationId.isDeleted=false
    and r.isDeleted=false
    """)
    Optional<Room> findById(Long accommodationId, Long roomId);
}
