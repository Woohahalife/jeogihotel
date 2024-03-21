package com.core.miniproject.src.room.repository;

import com.core.miniproject.src.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataJpaRoomRepository extends JpaRepository<Room, Long> {

    @Override
    Room save(Room room);

    @Override
    List<Room> findAll();

}
