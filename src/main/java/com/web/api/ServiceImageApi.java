package com.web.api;

import com.web.service.ServiceImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services-image")
@CrossOrigin
public class ServiceImageApi {

    @Autowired
    private ServiceImageService serviceImageService;

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        serviceImageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
