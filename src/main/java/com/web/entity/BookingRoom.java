package com.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "booking_room")
public class BookingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Double price;

    private Date fromDate;

    private Date toDate;

    private Integer numDay;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnoreProperties(value = {"bookingRooms","bookingServices"})
    private Booking booking;

}
