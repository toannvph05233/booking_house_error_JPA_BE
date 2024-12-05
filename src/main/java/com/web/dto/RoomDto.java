package com.web.dto;

import com.web.entity.Room;
import com.web.entity.Services;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomDto {

    private Room room;

    private List<String> listImage = new ArrayList<>();

    private List<Long> listUtilityId = new ArrayList<>();
}
