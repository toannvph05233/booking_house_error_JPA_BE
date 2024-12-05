package com.web.repository;

import com.web.entity.Booking;
import com.web.entity.BookingRoom;
import com.web.entity.BookingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingServiceRepository extends JpaRepository<BookingService,Long> {

    @Modifying
    @Transactional
    @Query(value = "delete ru from booking_service ru where ru.room_id = ?1", nativeQuery = true)
    int deleteByBooking(Long bookingId);

    @Query("select b from BookingService b where b.booking.id = ?1")
    public List<BookingService> findByBooking(Long bookingId);
}
