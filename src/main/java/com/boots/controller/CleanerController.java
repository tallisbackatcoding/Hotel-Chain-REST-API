package com.boots.controller;

import com.boots.entity.Employee;
import com.boots.entity.Hotel;
import com.boots.entity.Room;
import com.boots.repository.HotelRepository;
import com.boots.service.HotelService;
import com.boots.service.RoomService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/clean/")
public class CleanerController {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelService hotelService;
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;

    @GetMapping("/getnoncleanrooms")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> getNotCleanedRooms(Principal principal){
        System.out.println("Employee email: " + principal.getName());
        Employee employee = (Employee) userService.loadUserByUsername(principal.getName());

        Hotel hotel = hotelRepository.findById(employee.getHotel()).orElse(null);
        List<Room> nonCleanRooms = new ArrayList<>();
        for(Room room: hotel.getRooms()){
            if(!room.isClean()){
                nonCleanRooms.add(room);
            }
        }
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("nonCleanRooms", nonCleanRooms);
        responseJson.put("status", 0);
        return responseJson;
    }

    @PostMapping("/cleanroom/{roomNumber}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> cleanRoom(@PathVariable("roomNumber") int roomNumber,  Principal principal){
        System.out.println("Employee email: " + principal.getName());
        Employee employee = (Employee) userService.loadUserByUsername(principal.getName());
        Hotel hotel = hotelRepository.findById(employee.getHotel()).orElse(null);
        Room room = roomService.getRoom(roomNumber, hotel.getHotelId());
        room.setClean(true);
        roomService.addRoom(room);
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("status", 0);
        return responseJson;
    }
}
