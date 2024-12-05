package com.web.api;

import com.web.entity.Category;
import com.web.entity.Utilities;
import com.web.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilities")
@CrossOrigin
public class UtilityApi {

    @Autowired
    private UtilityService utilityService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody Utilities utilities){
        Utilities result = utilityService.save(utilities);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        utilityService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/findAll")
    public ResponseEntity<?> findAll(){
        List<Utilities> result = utilityService.findAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/admin/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        Utilities result = utilityService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
