package com.web.api;

import com.web.service.RoomImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room-image")
@CrossOrigin
public class RoomImageApi {

    @Autowired
    private RoomImageService roomImageService;

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        roomImageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
