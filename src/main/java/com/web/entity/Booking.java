package com.web.entity;

import com.web.enums.PayStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Date createdDate;

    private Date fromDate;

    private Date toDate;

    private Integer numDate;

    private Time createdTime;

    private Double amountRoom;

    private Double amountService;

    private String fullname;

    private String phone;

    private String cccd;

    private String note;

    private PayStatus payStatus;

    private Date payDate;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.REMOVE)
    private List<BookingRoom> bookingRooms;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.REMOVE)
    private List<BookingService> bookingServices;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "historyPay_id")
    private HistoryPay historyPay;
}
