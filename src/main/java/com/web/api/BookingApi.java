package com.web.api;

import com.web.dto.BookingDto;
import com.web.dto.CreatePayment;
import com.web.dto.DateResponse;
import com.web.dto.ResponsePayment;
import com.web.entity.Blog;
import com.web.entity.Booking;
import com.web.entity.BookingService;
import com.web.entity.Room;
import com.web.enums.PayStatus;
import com.web.exception.MessageException;
import com.web.repository.BookingRepository;
import com.web.repository.BookingRoomRepository;
import com.web.repository.BookingServiceRepository;
import com.web.service.BookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin
public class BookingApi {

    @Autowired
    private BookingServices bookingServices;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    @Autowired
    private BookingRoomRepository bookingRoomRepository;

    @GetMapping("/public/cal-date")
    public ResponseEntity<?> save(@RequestParam("from")Date from, @RequestParam("numDate") Integer numDate,
                                  @RequestParam(value = "id", required = false) Long typeRoom){
        DateResponse result = bookingServices.calDate(from, numDate,typeRoom);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/user/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody CreatePayment createPayment){
        ResponsePayment result = bookingServices.createUrl(createPayment);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/public/get-room-by-listId")
    public ResponseEntity<?> getListRoomById(@RequestBody List<Long> listRoomId){
        List<Room> result = bookingServices.getListRoom(listRoomId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/user/create-booking")
    public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto) throws Exception {
        bookingServices.create(bookingDto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @PostMapping("/admin/create-booking")
    public ResponseEntity<?> createBookingByAdmin(@RequestBody BookingDto bookingDto) throws Exception {
        bookingServices.createByAdmin(bookingDto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    @PostMapping("/user/my-booking")
    public ResponseEntity<?> myBooking(){
        List<Booking> result = bookingServices.myBooking();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/admin/all-room")
    public ResponseEntity<?> getListRoomById(@RequestParam(value = "from", required = false) Date from,
                                             @RequestParam(value = "to", required = false) Date to){
        List<Booking> result = bookingServices.allBooking(from, to);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/admin/update-status")
    public ResponseEntity<?> updateStatus(@RequestParam("id") Long id, @RequestParam("status") PayStatus status){
        Booking booking = bookingRepository.findById(id).get();
        booking.setPayStatus(status);
        if(status.equals(PayStatus.PAID)){
            booking.setPayDate(new Date(System.currentTimeMillis()));
        }
        bookingRepository.save(booking);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> updateStatus(@RequestParam("id") Long id){
        bookingServiceRepository.deleteByBooking(id);
        bookingRoomRepository.deleteByBooking(id);
        bookingRepository.deleteById(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
