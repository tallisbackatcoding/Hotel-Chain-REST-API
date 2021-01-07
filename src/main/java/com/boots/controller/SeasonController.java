package com.boots.controller;

import com.boots.entity.Employee;
import com.boots.entity.Hotel;
import com.boots.entity.Season;
import com.boots.repository.HotelRepository;
import com.boots.repository.SeasonRepository;
import com.boots.service.HotelService;
import com.boots.service.SeasonService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/season/")
public class SeasonController {
    @Autowired
    SeasonRepository seasonRepository;
    @Autowired
    SeasonService seasonService;
    @Autowired
    UserService userService;
    @Autowired
    HotelService hotelService;
    @PersistenceContext
    EntityManager em;


    @GetMapping("/getseasonsforhotel/{hotelId}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> getSeasonsById(@PathVariable("hotelId") int hotelId){

        Map<String, Object> responseJson = new HashMap<>();
        Hotel hotel = hotelService.getHotelById(hotelId);

        if(hotel == null){
            responseJson.put("status", 1);
        }else{
            responseJson.put("status", 0);
            responseJson.put("seasons", hotel.getSeasons());
        }
        return responseJson;
    }

    @PostMapping("/addseason")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> addHotel(@RequestBody Map<String, Object> requestJson, Principal principal){
        Map<String, Object> responseJson = new HashMap<>();
        Season season = new Season();
        Employee employee = (Employee)userService.loadUserByUsername(principal.getName());
        Date inDate = new Date((String)requestJson.get("inDate"));
        Date outDate = new Date((String)requestJson.get("outDate"));
        season.setHotel(hotelService.getHotelById(employee.getHotel()));
        season.setSeasonDiscount((int)requestJson.get("discount"));
        season.setSeasonInDate(inDate);
        season.setSeasonInDate(outDate);
        seasonRepository.save(season);
        requestJson.put("status", 0);
        return responseJson;
    }
/*
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
    }*/
}
