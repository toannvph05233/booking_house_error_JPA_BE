package com.web.service;

import com.mservice.config.Environment;
import com.mservice.models.PaymentResponse;
import com.mservice.models.QueryStatusTransactionResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.processor.QueryTransactionStatus;
import com.mservice.shared.constants.LogUtils;
import com.mservice.shared.constants.RequestType;
import com.web.dto.BookingDto;
import com.web.dto.CreatePayment;
import com.web.dto.DateResponse;
import com.web.dto.ResponsePayment;
import com.web.entity.*;
import com.web.enums.PayStatus;
import com.web.exception.MessageException;
import com.web.repository.*;
import com.web.utils.Contains;
import com.web.utils.UserUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class BookingServices {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingRoomRepository bookingRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private HistoryPayRepository historyPayRepository;

    @Autowired
    private UserRepository userRepository;




    public DateResponse calDate(Date from, Integer numDate,Long typeRoom){
        DateResponse dateResponse = new DateResponse();
        Long longTo = from.getTime() + (1000L * 60L * 60L * 24L * (numDate));
        Date to = new Date(longTo);
        Calendar c = Calendar.getInstance();
        c.setTime(to);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // 3
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(to);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd 'thg' MM, YYYY");
        String day = simpleDateFormat.format(to);
        dateResponse.setToDate(to);
        dateResponse.setFromDate(from);
        dateResponse.setNumDate(numDate);
        dateResponse.setDetailDate("Thứ "+dayOfWeek+", "+day);
        List<BookingRoom> bookingRooms = bookingRoomRepository.bookingRoomByDate(from, to);

        List<Room> rooms = null;
        if(typeRoom == null){
            rooms = roomRepository.findAll();
        }
        if(typeRoom != null){
            rooms = roomRepository.findByCategory(typeRoom);
        }

        for(int i=0; i< rooms.size(); i++){
            Boolean check = false;
            for(BookingRoom bk : bookingRooms){
                if(rooms.get(i).getId() == bk.getRoom().getId() && bk.getBooking().getPayStatus().equals(PayStatus.DEPOSITED)){
                    check = true;
                }
            }
            if(check){
                rooms.remove(rooms.get(i));
                --i;
            }
        }
        dateResponse.setRooms(rooms);
        dateResponse.setBookingRooms(bookingRooms);
        return dateResponse;
    }

    public List<Room> getListRoom(List<Long> listRoomId){
        return roomRepository.findAllById(listRoomId);
    }

    public ResponsePayment createUrl(CreatePayment createPayment){
        LogUtils.init();
        List<Room> rooms = getListRoom(createPayment.getListRoomId());
        Double amount = 0D;
        for(Room r : rooms){
            amount += r.getPrice();
        }
        Double payAmount = amount * Contains.percent;
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = String.valueOf(System.currentTimeMillis());
        Environment environment = Environment.selectEnv("dev");
        PaymentResponse captureATMMoMoResponse = null;
        try {
            captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(payAmount.longValue()), createPayment.getContent(), createPayment.getReturnUrl(), createPayment.getReturnUrl(), "", RequestType.PAY_WITH_ATM, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("url ====: "+captureATMMoMoResponse.getPayUrl());
        ResponsePayment responsePayment = new ResponsePayment(captureATMMoMoResponse.getPayUrl(),orderId,requestId);
        return responsePayment;
    }

    public void create(BookingDto bookingDto) throws Exception {
        Environment environment = Environment.selectEnv("dev");
        QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, bookingDto.getOrderId(), bookingDto.getRequestId());
        System.out.println("qqqq-----------------------------------------------------------"+queryStatusTransactionResponse.getMessage());
        if(queryStatusTransactionResponse.getResultCode() == 0){
            if(historyPayRepository.findByOrderIdAndRequestId(bookingDto.getOrderId(), bookingDto.getRequestId()).isPresent() == false){

                List<Room> rooms = getListRoom(bookingDto.getListRoomId());
                Double amount = 0D;
                Integer point = 0;
                for(Room r : rooms){
                    amount += r.getPrice();
                    if(r.getCategory().getId() == 2){
                        point += 10;
                    }
                    else if(r.getCategory().getId() == 4){
                        point += 20;
                    }
                    else if(r.getCategory().getId() == 5){
                        point += 30;
                    }
                }

                HistoryPay historyPay = new HistoryPay();
                historyPay.setRequestId(bookingDto.getRequestId());
                historyPay.setOrderId(bookingDto.getOrderId());
                historyPay.setAmount(amount);
                historyPay.setCreatedDate(new Date(System.currentTimeMillis()));
                historyPay.setCreatedTime(new Time(System.currentTimeMillis()));
                HistoryPay hi =  historyPayRepository.save(historyPay);

                Long longTo = bookingDto.getFromDate().getTime() + (1000L * 60L * 60L * 24L * (bookingDto.getNumDate()));
                Date toDate = new Date(longTo);

                User u = userUtils.getUserWithAuthority();
                if(u.getPoint() != null){
                    if(u.getPoint() == 100){
                        amount = amount - (amount * 5 % 100);
                        u.setPoint(0);
                    }
                }
                Booking booking = new Booking();
                booking.setCreatedDate(new Date(System.currentTimeMillis()));
                booking.setCreatedTime(new Time(System.currentTimeMillis()));
                booking.setUser(u);
                booking.setAmountRoom(amount);
                booking.setAmountService(0D);
                booking.setPayStatus(PayStatus.DEPOSITED);
                booking.setFullname(bookingDto.getFullname());
                booking.setPhone(bookingDto.getPhone());
                booking.setCccd(bookingDto.getCccd());
                booking.setNote(bookingDto.getNote());
                booking.setFromDate(bookingDto.getFromDate());
                booking.setToDate(toDate);
                booking.setNumDate(bookingDto.getNumDate());
                booking.setHistoryPay(hi);
                Booking result = bookingRepository.save(booking);

                if(u.getPoint() == null){
                    u.setPoint(0);
                }
                u.setPoint(u.getPoint() + point);
                userRepository.save(u);


                for(Room r : rooms){
                    BookingRoom bookingRoom = new BookingRoom();
                    bookingRoom.setBooking(result);
                    bookingRoom.setRoom(r);
                    bookingRoom.setFromDate(bookingDto.getFromDate());
                    bookingRoom.setNumDay(bookingDto.getNumDate());
                    bookingRoom.setPrice(r.getPrice());
                    bookingRoom.setToDate(toDate);
                    bookingRoomRepository.save(bookingRoom);
                }
            }
        }
        else{
            throw new MessageException("Thanh toán cọc thất bại");
        }
    }


    public List<Booking> myBooking(){
        return bookingRepository.myBooking(userUtils.getUserWithAuthority().getId());
    }

    public List<Booking> allBooking(Date from, Date to){
        List<Booking> list = null;
        if(from == null || to == null){
            from = Date.valueOf("2000-01-01");
            to = Date.valueOf("2100-01-01");
        }
        list = bookingRepository.allBooking(from, to);
        return list;
    }


    public void createByAdmin(BookingDto bookingDto) throws Exception {
        List<Room> rooms = getListRoom(bookingDto.getListRoomId());
        Double amount = 0D;
        for(Room r : rooms){
            amount += r.getPrice();
        }

        Long longTo = bookingDto.getFromDate().getTime() + (1000L * 60L * 60L * 24L * (bookingDto.getNumDate()));
        Date toDate = new Date(longTo);

        Booking booking = new Booking();
        booking.setCreatedDate(new Date(System.currentTimeMillis()));
        booking.setCreatedTime(new Time(System.currentTimeMillis()));
        booking.setAmountRoom(amount);
        booking.setAmountService(0D);
        booking.setPayStatus(PayStatus.DEPOSITED);
        booking.setFullname(bookingDto.getFullname());
        booking.setPhone(bookingDto.getPhone());
        booking.setCccd(bookingDto.getCccd());
        booking.setNote(bookingDto.getNote());
        booking.setFromDate(bookingDto.getFromDate());
        booking.setToDate(toDate);
        booking.setNumDate(bookingDto.getNumDate());
        Booking result = bookingRepository.save(booking);


        for(Room r : rooms){
            BookingRoom bookingRoom = new BookingRoom();
            bookingRoom.setBooking(result);
            bookingRoom.setRoom(r);
            bookingRoom.setFromDate(bookingDto.getFromDate());
            bookingRoom.setNumDay(bookingDto.getNumDate());
            bookingRoom.setPrice(r.getPrice());
            bookingRoom.setToDate(toDate);
            bookingRoomRepository.save(bookingRoom);
        }
    }

}
