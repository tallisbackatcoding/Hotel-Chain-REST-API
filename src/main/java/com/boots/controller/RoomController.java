package com.boots.controller;

import com.boots.entity.Hotel;
import com.boots.entity.Room;
import com.boots.entity.RoomType;
import com.boots.repository.HotelRepository;
import com.boots.repository.RoomRepository;
import com.boots.service.HotelService;
import com.boots.service.RoomService;
import com.boots.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/room/")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomService roomService;
    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    HotelService hotelService;

    @GetMapping("/getroomsforhotel/{hotelId}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true"   )
    public List<Room> getRoomsForHotel(@PathVariable("hotelId") int hotelId){
        return roomService.getAllRoomsForHotel(hotelId);
    }

    @GetMapping("/{hotelId}/{roomNumber}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> getRoomByIds(@PathVariable("roomNumber") int roomNumber, @PathVariable("hotelId") int hotelId){

        Map<String, Object> responseJson = new HashMap<>();
        Room room = roomService.getRoom(roomNumber, hotelId);
        responseJson.put("room", room);
        if(room == null){
            responseJson.put("status", 1);
        }else{
            responseJson.put("status", 0);
        }

        return responseJson;
    }

    @PostMapping("/addroom")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> addRoom(@RequestBody Map<String, String> requestJson){
        Map<String, Object> responseJson = new HashMap<>();

        int roomNumber = Integer.parseInt(requestJson.get("roomNumber"));
        int hotelId = Integer.parseInt(requestJson.get("hotelId"));
        int roomFloor = Integer.parseInt(requestJson.get("roomFloor"));
        int roomTypeId = Integer.parseInt(requestJson.get("roomTypeId"));

        int status = 0;
        //if(roomService.getRoom(roomNumber, hotelId) != null){
            //status = 1;
        //}else{
            System.out.println("Inside else");
            Room room = new Room();
            RoomType roomType = roomTypeService.getRoomType(roomTypeId, hotelId);
            room.setRoomNumber(roomNumber);
            room.setHotel(hotelService.getHotelById(hotelId));
            room.setRoomFloor(roomFloor);
            room.setRoomType(roomType);
            room.setReservationList(null);
            System.out.println(room.getHotelId());
            roomService.addRoom(room);
            responseJson.put("room", room);

        //}
        responseJson.put("status", status);
        return responseJson;
    }

    @DeleteMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> removeHotel(@RequestParam("hotelId") int hotelId, @RequestParam("roomNumber") int roomNumber){
        Map<String, Object> responseJson = new HashMap<>();
        if(roomService.deleteRoom(roomNumber, hotelId)){
            responseJson.put("status", 0);
        }else{
            responseJson.put("status", 1);
        }
        return responseJson;
    }


}
