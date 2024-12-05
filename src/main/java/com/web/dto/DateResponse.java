package com.web.dto;

import com.web.entity.BookingRoom;
import com.web.entity.Room;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class DateResponse {

    private Date fromDate;

    private Date toDate;

    private Integer numDate;

    private String detailDate;

    private List<Room> rooms = new ArrayList<>();

    private List<BookingRoom> bookingRooms = new ArrayList<>();
}
