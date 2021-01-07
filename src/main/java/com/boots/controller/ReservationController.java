package com.boots.controller;

import com.boots.entity.*;
import com.boots.mymodel.RoomModel;
import com.boots.repository.ReservationRepository;
import com.boots.service.HotelService;
import com.boots.service.ReservationService;
import com.boots.service.RoomService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/reserve")
@RestController
public class ReservationController {

    @Autowired
    private UserService userService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/getallreservations")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    @PostMapping("/addreservation")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> addReservation(@RequestBody Map<String, String> requestJson, Principal principal){
        User u = (User) userService.loadUserByUsername(principal.getName());
        System.out.println("Adding Reservation for: " + u.getUsername());

        Map<String, Object> responseJson = new HashMap<>();
        Room freeRoom = null;
        for(Room room: roomService.findRoomsWithParams(requestJson)){
            System.out.println("i am");
            if(reservationService.isRoomFree(room, new Date(requestJson.get("inDate")), new Date(requestJson.get("outDate")))){
                freeRoom = room;
                System.out.println("i am hereeee");
                break;
            }
        }
        if(freeRoom != null){
            responseJson.put("status", 0);
            Reservation reservation = new Reservation(u, freeRoom,
                    new Date(requestJson.get("inDate")), new Date(requestJson.get("outDate")), new Date());
            responseJson.put("reservation", reservation);
            freeRoom.setClean(false);
            List<Long> roleIds = new ArrayList<>();
            for(Role r: u.getRoles()){
                roleIds.add(r.getId());
            }
            Map<String, Object> prices = reservationService.calculateDiscounts(roleIds,
                    new Date(requestJson.get("inDate")),
                            freeRoom.getHotelId(), freeRoom.getRoomType().getRoomTypePrice());
            reservation.setCost((int)prices.get("finalPrice"));
            reservationService.addReservation(reservation, freeRoom);
        }else{
            responseJson.put("status", 1);
        }
        return responseJson;
    }
    @PostMapping("/mergereservation")
    public Map<String, Object> mergeReservation(@RequestBody Map<String, String> requestJson, Principal principal){
        Map<String, Object> responseJson = new HashMap<>();
        try{
            Reservation reservation = reservationRepository.findById(Integer.parseInt(requestJson.get("reservationId"))).orElse(null);
            if(reservation == null){
                reservation = new Reservation();
            }
            reservation.setCost(Integer.parseInt(requestJson.get("cost")));
            reservation.setInDate(new Date(requestJson.get("inDate")));
            reservation.setOutDate(new Date(requestJson.get("outDate")));
            reservation.setOutDate(new Date(requestJson.get("outDate")));
            reservation.setReservationId(Integer.parseInt(requestJson.get("reservationId")));
            reservation.setRoom(roomService.getRoom(Integer.parseInt(requestJson.get("roomNumber")),
                    Integer.parseInt(requestJson.get("hotelId"))));
            reservation.setUser(userService.findUserById(Long.parseLong(requestJson.get("userId"))));
            reservationRepository.save(reservation);
            responseJson.put("status", 0);
        }catch (Exception e){
            e.printStackTrace();
            responseJson.put("status", 1);
        }
        return responseJson;
    }
    @DeleteMapping("deletereservation/{hotelId}")
    public Map<String, Object> deleteReservation(@PathVariable("hotelId") int hotelId){
        Map<String, Object> responseJson = new HashMap<>();
        try {
            reservationRepository.deleteById(hotelId);
            responseJson.put("status", 0);
        }catch (Exception e){
            e.printStackTrace();
            responseJson.put("status", 1);
        }
        return responseJson;
    }

    @PostMapping("findrooms-withindate")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> findRoomsInDateRange(@RequestBody Map<String, String> requestJson, Principal principal){

        User u = (User) userService.loadUserByUsername(principal.getName());
        List<Long> roleIds = new ArrayList<>();
        for(Role r: u.getRoles()){
            roleIds.add(r.getId());
        }
        System.out.println(requestJson);
        Date inDate = new Date(requestJson.get("inDate"));
        Date outDate = new Date(requestJson.get("outDate"));

        Map<String, Object> responseJson = new HashMap<>();
        List<Room> roomsForHotel = null;
        int status = 0;
        try {
            roomsForHotel = roomService.findRoomsWithParams(requestJson);
        }catch (Exception e){
            status = 1;
        }
        if(status == 0){
            System.out.println(roomsForHotel);
            List<RoomModel> availableRoomTypes = reservationService.findRoomsInDateRange(roomsForHotel, inDate, outDate);
            Map<Integer, Map<String, Object>> avRoomTypes = new HashMap<>();
            for(RoomModel rm: availableRoomTypes){
                Hotel hotel = hotelService.getHotelById(rm.getHotelId());


                int hotelId = hotel.getHotelId();
                String hotelName = hotel.getHotelName();
                String hotelAddress = hotel.getHotelAddress();

                if(!avRoomTypes.containsKey(hotelId)){
                    avRoomTypes.put(hotelId, new HashMap<>());
                    avRoomTypes.get(hotelId).put("roomTypes", new ArrayList<RoomType>());
                }
                avRoomTypes.get(hotelId).put("hotelId", hotelId);
                avRoomTypes.get(hotelId).put("hotelName", hotelName);
                avRoomTypes.get(hotelId).put("hotelAddress", hotelAddress);
                //avRoomTypes.get(hotelId).put("prices", reservationService.calculateDiscounts(roleIds, inDate, hotelId, rm.getRoomType().getRoomTypePrice()));
                Map<String, Object> prices =  reservationService.calculateDiscounts(roleIds, inDate, hotelId, rm.getRoomType().getRoomTypePrice());
                rm.setDiscounts((Map<String, Integer>) prices.get("discounts"));
                rm.setFinalPrice((int)prices.get("finalPrice"));
                rm.setOriginalPrice((int)prices.get("originalPrice"));
                ((List<Object>)avRoomTypes.get(hotelId).get("roomTypes")).add(rm);
                avRoomTypes.get(hotelId).put("hotelAddress", hotelAddress);
            }
            List<Object> hotelArray = new ArrayList<>();
            for(Integer key: avRoomTypes.keySet()){
                hotelArray.add(avRoomTypes.get(key));
            }
            responseJson.put("roomTypes", hotelArray);
        }else{
            responseJson.put("roomTypes", null);
        }
        responseJson.put("status", status);
        responseJson.put("params", requestJson);
        return responseJson;
    }
}