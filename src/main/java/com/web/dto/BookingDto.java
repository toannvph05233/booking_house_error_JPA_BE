package com.web.dto;

import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookingDto {

    private Date fromDate;

    private Integer numDate;

    private String fullname;

    private String phone;

    private String cccd;

    private String note;

    private String requestId;

    private String orderId;

    private List<Long> listRoomId = new ArrayList<>();

}
