package com.web.repository;

import com.web.entity.Booking;
import com.web.entity.BookingRoom;
import com.web.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface BookingRoomRepository extends JpaRepository<BookingRoom,Long> {


    @Modifying
    @Transactional
    @Query(value = "delete ru from booking_room ru where ru.booking_id = ?1", nativeQuery = true)
    int deleteByBooking(Long bookingId);

    @Query("select b from BookingRoom b where " +
            "(b.fromDate <= ?1 and b.toDate >= ?2) or " +
            "(b.fromDate <= ?1 and b.toDate <= ?2 and b.toDate >= ?1) or " +
            "(b.fromDate >= ?1 and b.toDate <= ?2) or " +
            "(b.fromDate >= ?1 and b.toDate >= ?2 and b.fromDate <= ?2)")
    public List<BookingRoom> bookingRoomByDate(Date from, Date to);


    @Query("select b from BookingRoom b where b.booking.id = ?1")
    public List<BookingRoom> findByBooking(Long bookingId);
}
