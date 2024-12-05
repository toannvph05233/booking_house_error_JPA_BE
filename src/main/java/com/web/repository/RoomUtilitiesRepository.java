package com.web.repository;

import com.web.entity.Category;
import com.web.entity.Room;
import com.web.entity.RoomUtilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RoomUtilitiesRepository extends JpaRepository<RoomUtilities,Long> {

    @Modifying
    @Transactional
    @Query(value = "delete ru from room_utilities ru where ru.room_id = ?1", nativeQuery = true)
    int deleteByRoom(Long roomId);
}
