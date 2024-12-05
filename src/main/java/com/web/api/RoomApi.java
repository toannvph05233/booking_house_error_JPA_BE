package com.web.api;

import com.web.dto.RoomDto;
import com.web.dto.ServiceDto;
import com.web.entity.Room;
import com.web.entity.Services;
import com.web.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class RoomApi {

    @Autowired
    private RoomService roomService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody RoomDto roomDto){
        Room result = roomService.save(roomDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        roomService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/findAll")
    public ResponseEntity<?> findAll(){
        List<Room> result = roomService.findAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/admin/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        Room result = roomService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findById")
    public ResponseEntity<?> findByIdUser(@RequestParam("id") Long id){
        Room result = roomService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
