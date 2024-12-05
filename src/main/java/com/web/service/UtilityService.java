package com.web.service;

import com.web.entity.Category;
import com.web.entity.Utilities;
import com.web.enums.CategoryType;
import com.web.repository.UtilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityService {

    @Autowired
    private UtilitiesRepository utilitiesRepository;

    public Utilities save(Utilities utilities){
        Utilities result = utilitiesRepository.save(utilities);
        return result;
    }

    public List<Utilities> findAll (){
        List<Utilities> result = utilitiesRepository.findAll();
        return result;
    }

    public Utilities findById(Long id){
        return utilitiesRepository.findById(id).get();
    }

    public void delete(Long id){
        utilitiesRepository.deleteById(id);
    }
}
