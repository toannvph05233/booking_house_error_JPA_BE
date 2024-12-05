package com.web.service;

import com.web.entity.BookingRoom;
import com.web.repository.BookingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingRoomService {

    @Autowired
    private BookingRoomRepository bookingRoomRepository;

    public List<BookingRoom> findByBooking(Long bookingId){
        return bookingRoomRepository.findByBooking(bookingId);
    }


}
