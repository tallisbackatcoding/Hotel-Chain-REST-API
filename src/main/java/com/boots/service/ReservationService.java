package com.boots.service;

import com.boots.entity.*;
import com.boots.mymodel.RoomModel;
import com.boots.repository.HotelRepository;
import com.boots.repository.ReservationRepository;
import com.boots.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleStatus;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.*;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private WeekdayService weekdayService;
    @Autowired
    private UserService userService;
    @PersistenceContext
    private EntityManager em;

    public List<Reservation> getAllReservations(){
        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findAll().forEach(reservations::add);
        return reservations;
    }

    public boolean isRoomFree(Room room, Date inDate, Date outDate){
        if(!room.isClean()){
            return false;
        }
        List<Reservation> reservationsForRoom = room.getReservationList();
        if(reservationsForRoom != null){
            for(Reservation reservation: reservationsForRoom){
                if(!reservation.getInDate().before(outDate)){
                }else if(!inDate.before(reservation.getOutDate())){
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public boolean addReservation(Reservation reservation, Room room){
        reservationRepository.save(reservation);
        return true;
    }

    public Map<String, Object> calculateDiscounts(List<Long> roleIds, Date inDate, int hotelId, int price){
        int seasonDiscount = 0;
        int roleDiscount = 0;
        if(seasonService.findSeasonByDate(inDate, hotelId) != null){
            seasonDiscount = seasonService.findSeasonByDate(inDate, hotelId).getSeasonDiscount();
        }
        try{
            for(Long l: roleIds){
                roleDiscount += roleRepository.findById(l).get().getRoleDiscount();
            }
        }catch (Exception e){
            roleDiscount = 0;
        }


        int weekDayDiscount = weekdayService.getWeekDayDiscountById(inDate.getDay());
        Map<String, Object> prices = new HashMap<>();
        prices.put("discounts", new HashMap<String, Integer>());
        int totalDiscount = seasonDiscount + roleDiscount + weekDayDiscount;
        int finalPrice = (int)((double)price*(1-totalDiscount/100.0));
        prices.put("finalPrice", finalPrice);
        prices.put("originalPrice", price);
        try{
            ((Map<String, Object>)prices.get("discounts")).put("seasonDiscount", seasonDiscount);
            ((Map<String, Object>)prices.get("discounts")).put("weekDayDiscount", weekDayDiscount);
            ((Map<String, Object>)prices.get("discounts")).put("roleDiscount", roleDiscount);
        }catch (Exception e){
            System.out.println("Couldn't convert Object into Map in calculatePrices Function");
        }

        return prices;
    }
    public List<RoomModel> findRoomsInDateRange(List<Room> roomsForHotel, Date inDate, Date outDate){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser;
        Employee currentEmployee = null;
        try{
            currentUser =  (User)userService.loadUserByUsername(authentication.getName());
        }catch (Exception e){
            currentUser = null;
            currentEmployee = (Employee)userService.loadUserByUsername(authentication.getName());
        }

        Set<String> roomTypes = new TreeSet<>();

        List<RoomModel> availableRooms = new ArrayList<>();
        for(Room room: roomsForHotel){
            String ids = room.getRoomType() + "_" + room.getHotelId();
            if(roomTypes.contains(ids)){
                continue;
            }
            List<Reservation> allReservationsForRoom = room.getReservationList();//getReservationsByRoomId(room.getHotelId(), room.getRoomNumber());
            boolean available = true;
            for(Reservation reservation: allReservationsForRoom){
                if(!reservation.getInDate().before(outDate)){
                    available = true;
                }else if(!inDate.before(reservation.getOutDate())){
                    available = true;
                }else{
                    available = false;
                }
            }
            if(allReservationsForRoom.size() == 0){
                available = true;
            }
            if(available){
                ids = room.getRoomType() + "_" + room.getHotelId();
                roomTypes.add(ids);
                List<Long> roleIds = new ArrayList<>();
                Set<Role> userRoles;
                if(currentUser != null){
                    userRoles = currentUser.getRoles();
                }else{
                    if(currentEmployee != null){
                        userRoles = currentEmployee.getRoles();
                    }else{
                        return new ArrayList<>();
                    }

                }

                userRoles.forEach(role -> {
                    roleIds.add(role.getId());
                });
                Map<String, Object> prices = calculateDiscounts(roleIds, inDate, room.getHotelId(), room.getRoomType().getRoomTypePrice());
                availableRooms.add(new RoomModel(room.getRoomType(), prices));
            }
        }
        return availableRooms;

    }
    /*
    public List<Reservation> getReservationsForUser(int userId){
        return reservationRepository.findById();
    }*/

}
