package com.web.service;

import com.web.dto.BookingServiceDto;
import com.web.entity.Booking;
import com.web.entity.BookingRoom;
import com.web.entity.BookingService;
import com.web.entity.Services;
import com.web.repository.BookingRepository;
import com.web.repository.BookingRoomRepository;
import com.web.repository.BookingServiceRepository;
import com.web.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class BookingServiceService {

    @Autowired
    private BookingServiceRepository bookingServiceRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<BookingService> findByBooking(Long bookingId){
        return bookingServiceRepository.findByBooking(bookingId);
    }

    public void create(List<BookingServiceDto> list, Long idBooking){
        Booking booking = bookingRepository.findById(idBooking).get();
        Double total = booking.getAmountService();
        for(BookingServiceDto b : list){
            BookingService bookingService = new BookingService();
            Services services = servicesRepository.findById(b.getId()).get();
            total += services.getPrice() * b.getQuantity();
            bookingService.setBooking(booking);
            bookingService.setServices(services);
            bookingService.setCreatedDate(new Date(System.currentTimeMillis()));
            bookingService.setCreatedTime(new Time(System.currentTimeMillis()));
            bookingService.setQuantity(b.getQuantity());
            bookingService.setPrice(services.getPrice());
            bookingServiceRepository.save(bookingService);
        }
        booking.setAmountService(total);
        bookingRepository.save(booking);
    }

    public void delete(Long id){
        BookingService bookingService = bookingServiceRepository.findById(id).get();
        bookingService.getBooking().setAmountService(bookingService.getBooking().getAmountService() - bookingService.getPrice() * bookingService.getQuantity());
        bookingRepository.save(bookingService.getBooking());
        bookingServiceRepository.delete(bookingService);
    }
}
