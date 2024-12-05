package com.web.repository;

import com.web.entity.HistoryPay;
import com.web.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("select r from Room r where r.category.id = ?1")
    public List<Room> findByCategory(Long categoryId);
}
