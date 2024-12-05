package com.web.service;

import com.web.dto.RoomDto;
import com.web.dto.ServiceDto;
import com.web.entity.*;
import com.web.repository.RoomImageRepository;
import com.web.repository.RoomRepository;
import com.web.repository.RoomUtilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Autowired
    private RoomUtilitiesRepository roomUtilitiesRepository;

    public Room save(RoomDto roomDto){
        Room result = roomRepository.save(roomDto.getRoom());
        for(String s : roomDto.getListImage()){
            RoomImage roomImage = new RoomImage();
            roomImage.setImage(s);
            roomImage.setRoom(result);
            roomImageRepository.save(roomImage);
        }
        roomUtilitiesRepository.deleteByRoom(result.getId());
        for(Long id : roomDto.getListUtilityId()){
            RoomUtilities roomUtilities = new RoomUtilities();
            roomUtilities.setRoom(result);
            Utilities utilities = new Utilities();
            utilities.setId(id);
            roomUtilities.setUtilities(utilities);
            roomUtilitiesRepository.save(roomUtilities);
        }
        return result;
    }

    public List<Room> findAll (){
        List<Room> result = roomRepository.findAll();
        return result;
    }

    public Room findById(Long id){
        return roomRepository.findById(id).get();
    }

    public void delete(Long id){
        roomRepository.deleteById(id);
    }
}
