package com.boots.controller;

import com.boots.entity.Hotel;
import com.boots.repository.HotelRepository;
import com.boots.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/hotel/")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelService hotelService;

    @GetMapping("/{hotelId}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> getHotelById(@PathVariable("hotelId") int hotelId){
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("hotel", hotel);
        if(hotel == null){
            responseJson.put("status", 1);
        }else{
            responseJson.put("status", 0);
        }
        return responseJson;
    }

    @PostMapping("/addhotel")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> addHotel(@RequestBody Map<String, Object> requestJson){
        Map<String, Object> responseJson = new HashMap<>();

        int status = 0;
        List<String> hotelPhones = (List<String>)requestJson.get("hotelPhones");
        String hotelName = (String)requestJson.get("hotelName");
        String hotelAddress = (String)requestJson.get("hotelAddress");
        Hotel hotel = hotelService.findHotelByNameAndCity(hotelName, hotelAddress);

        if(hotel == null){
            hotel = new Hotel();
        }
        hotel.setHotelName(hotelName);
        hotel.setHotelAddress(hotelAddress);
        hotel.setHotelPhones(hotelPhones);
        hotelService.addHotel(hotel);
        responseJson.put("hotel", hotel);
        responseJson.put("status", status);
        return responseJson;
    }

    @DeleteMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> removeHotel(@RequestParam("hotelId") int hotelId){
        Map<String, Object> responseJson = new HashMap<>();
        if(hotelService.removeHotelById(hotelId)){
            responseJson.put("status", 0);
        }else{
            responseJson.put("status", 1);
        }
        return responseJson;
    }
    @PostMapping("/addphone")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> addPhoneToHotel(@RequestBody Map<String, Object> requestJson){
        Map<String, Object> responseJson = new HashMap<>();
        try {
            hotelService.addPhone((int)requestJson.get("hotelId"), (String)requestJson.get("phoneNumber"));
            responseJson.put("status", "0");
        }catch (Exception e){
            responseJson.put("status", "1");
        }
        return responseJson;
    }
}
