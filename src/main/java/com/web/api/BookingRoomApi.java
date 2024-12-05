package com.web.api;

import com.web.entity.Booking;
import com.web.entity.BookingRoom;
import com.web.service.BookingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-room")
@CrossOrigin
public class BookingRoomApi {

    @Autowired
    private BookingRoomService bookingRoomService;

    @PostMapping("/public/find-by-booking")
    public ResponseEntity<?> myBooking(@RequestParam Long id){
        List<BookingRoom> result = bookingRoomService.findByBooking(id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }



}
