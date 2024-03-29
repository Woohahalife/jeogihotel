package com.core.miniproject.src.image.repository;

import com.core.miniproject.src.image.domain.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
}
