package com.web.service;

import com.web.repository.RoomImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomImageService {

    @Autowired
    private RoomImageRepository roomImageRepository;

    public void delete(Long id){
        roomImageRepository.deleteById(id);
    }
}
