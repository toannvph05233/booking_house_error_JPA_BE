package com.web.api;

import com.web.dto.ServiceDto;
import com.web.entity.Services;
import com.web.entity.Utilities;
import com.web.repository.ServicesRepository;
import com.web.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin
public class ServiceApi {

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private ServicesRepository servicesRepository;

    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody ServiceDto servicesDto){
        Services result = servicesService.save(servicesDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        servicesService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/findAll")
    public ResponseEntity<?> findAll(){
        List<Services> result = servicesService.findAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/admin/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        Services result = servicesService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findById")
    public ResponseEntity<?> findByIdAndUser(@RequestParam("id") Long id){
        Services result = servicesService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findByCategory")
    public ResponseEntity<?> findByCategory(@RequestParam("id") Long id){
        List<Services> result = servicesService.findByCategory(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/public/find-by-list-id")
    public ResponseEntity<?> findByListId(@RequestBody List<Long> list){
        List<Services> result = servicesRepository.findAllById(list);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
