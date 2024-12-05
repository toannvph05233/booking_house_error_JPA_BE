package com.web.dto;

import com.web.entity.Services;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServiceDto {

    private Services services;

    private List<String> listImage = new ArrayList<>();
}
