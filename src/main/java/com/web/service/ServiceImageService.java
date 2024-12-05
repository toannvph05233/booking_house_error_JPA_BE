package com.web.service;

import com.web.repository.ServiceImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceImageService {

    @Autowired
    private ServiceImageRepository serviceImageRepository;

    public void delete(Long id){
        serviceImageRepository.deleteById(id);
    }
}
