package com.boots.controller;

import com.boots.entity.Room;
import com.boots.entity.RoomType;
import com.boots.repository.RoomRepository;
import com.boots.repository.RoomTypeRepository;
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
@RequestMapping("/roomtype/")
public class RoomTypeController {
    @Autowired
    RoomTypeRepository roomTypeRepository;
    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    HotelService hotelService;

    @GetMapping("/getroomtypesforhotel/{hotelId}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<RoomType> getRoomsForHotel(@PathVariable("hotelId") int hotelId){
        return roomTypeService.getAllRoomsTypesForHotel(hotelId);
    }

    @GetMapping("/{hotelId}/{roomTypeId}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> getRoomByIds(@PathVariable("roomTypeId") int roomTypeId, @PathVariable("hotelId") int hotelId){
        Map<String, Object> responseJson = new HashMap<>();
        RoomType roomType = roomTypeService.getRoomType(roomTypeId, hotelId);
        responseJson.put("roomType", roomType);
        if(roomType == null){
            responseJson.put("status", 1);
        }else{
            responseJson.put("status", 0);
        }
        return responseJson;
    }
    @PostMapping("/addroomtype")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> addRoom(@RequestBody Map<String, Object> requestJson){
        Map<String, Object> responseJson = new HashMap<>();

        int hotelId = Integer.parseInt(requestJson.get("hotelId"));
        int roomTypeId = (int)requestJson.get("roomTypeId");
        int people = (int)requestJson.get("roomTypePeople");
        int size = (int)requestJson.get("roomTypeSize");
        int price = (int)requestJson.get("roomTypePrice");
        List<String> features = (List<String>)requestJson.get("features");

        int status = 0;
            RoomType roomType = new RoomType();
            roomType.setRoomTypeId(roomTypeId);
            roomType.setHotel(hotelService.getHotelById(hotelId));
            roomType.setRoomTypePeople(people);
            roomType.setRoomTypeSize(size);
            roomType.setRoomTypePrice(price);
            roomType.setFeatures(features);
            roomTypeService.addRoomType(roomType);
            responseJson.put("roomType", roomType);
        //}
        responseJson.put("status", status);
        return responseJson;
    }

    @DeleteMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> removeHotel(@RequestParam("hotelId") int hotelId, @RequestParam("roomTypeId") int roomTypeId){
        Map<String, Object> responseJson = new HashMap<>();
        if(roomTypeService.deleteRoomType(roomTypeId, hotelId)){
            responseJson.put("status", 0);
        }else{
            responseJson.put("status", 1);
        }
        return responseJson;
    }
}
