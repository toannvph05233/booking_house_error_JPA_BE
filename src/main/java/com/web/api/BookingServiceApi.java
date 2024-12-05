package com.web.api;

import com.web.dto.BookingServiceDto;
import com.web.entity.BookingRoom;
import com.web.entity.BookingService;
import com.web.service.BookingRoomService;
import com.web.service.BookingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-service")
@CrossOrigin
public class BookingServiceApi {

    @Autowired
    private BookingServiceService bookingServiceService;

    @PostMapping("/public/find-by-booking")
    public ResponseEntity<?> myBooking(@RequestParam Long id){
        List<BookingService> result = bookingServiceService.findByBooking(id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@RequestBody List<BookingServiceDto> list, @RequestParam("id") Long bookingId){
        bookingServiceService.create(list,bookingId);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        bookingServiceService.delete(id);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


}
