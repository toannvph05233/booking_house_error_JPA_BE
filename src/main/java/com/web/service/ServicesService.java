package com.web.service;

import com.web.dto.ServiceDto;
import com.web.entity.Category;
import com.web.entity.ServiceImage;
import com.web.entity.Services;
import com.web.entity.Utilities;
import com.web.repository.ServiceImageRepository;
import com.web.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServiceImageRepository serviceImageRepository;

    public Services save(ServiceDto servicesDto){
        Services result = servicesRepository.save(servicesDto.getServices());
        for(String s : servicesDto.getListImage()){
            ServiceImage serviceImage = new ServiceImage();
            serviceImage.setImage(s);
            serviceImage.setServices(result);
            serviceImageRepository.save(serviceImage);
        }
        return result;
    }

    public List<Services> findAll (){
        List<Services> result = servicesRepository.findAll();
        return result;
    }

    public List<Services> findByCategory (Long id){
        List<Services> result = servicesRepository.findByCategory(id);
        return result;
    }

    public Services findById(Long id){
        return servicesRepository.findById(id).get();
    }

    public void delete(Long id){
        servicesRepository.deleteById(id);
    }
}
