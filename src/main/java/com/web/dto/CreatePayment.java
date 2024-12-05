package com.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreatePayment {

    private String returnUrl;

    private String content;

    private List<Long> listRoomId = new ArrayList<>();
}
