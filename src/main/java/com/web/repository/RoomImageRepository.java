package com.web.repository;

import com.web.entity.Category;
import com.web.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomImageRepository extends JpaRepository<RoomImage,Long> {
}
