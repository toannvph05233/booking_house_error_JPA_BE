package com.web.repository;

import com.web.entity.Booking;
import com.web.entity.BookingRoom;
import com.web.entity.User;
import com.web.enums.PayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("select b from Booking b where b.user.id = ?1")
    public List<Booking> myBooking(Long userId);

    @Query("SELECT SUM(b.amountRoom + b.amountService) FROM Booking b WHERE QUARTER(b.payDate) = :quarter AND YEAR(b.payDate) = :year AND b.payStatus = :payStatus")
    Double calRevenueByQuarter(@Param("quarter") Integer quarter, @Param("year") Integer year, @Param("payStatus") PayStatus payStatus);

    @Query("SELECT SUM(b.amountRoom + b.amountService) FROM Booking b WHERE MONTH(b.payDate) = :month AND YEAR(b.payDate) = :year AND b.payStatus = :payStatus")
    Double calDt(@Param("month") Integer month, @Param("year") Integer year, @Param("payStatus") PayStatus payStatus);

    @Query("select b from Booking b where b.createdDate >= ?1 and b.createdDate <= ?2")
    public List<Booking> allBooking(Date from, Date to);
}
