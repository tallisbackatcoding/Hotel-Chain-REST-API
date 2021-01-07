package com.boots.service;

import com.boots.entity.Room;
import com.boots.idclasses.RoomIds;
import com.boots.repository.HotelRepository;
import com.boots.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @PersistenceContext
    private EntityManager em;

    public List<Room> getAllRooms(){
        return new ArrayList<>(roomRepository.findAll());
    }

    public Room getRoom(int roomNumber, int hotelId) {
        try {
            List <Room> rooms = em.createQuery("SELECT r FROM Room r WHERE r.hotel.hotelId = :hotelId and r.roomNumber = :roomNumber", Room.class)
                    .setParameter("hotelId", hotelId).setParameter("roomNumber", roomNumber).getResultList();
            System.out.println(rooms);
            if(rooms.isEmpty()){
                return null;
            }else{
                return rooms.get(0);
            }
        } catch (NoResultException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<Room> getAllRoomsForHotel(int hotelId){
        return em.createQuery("SELECT r FROM Room r WHERE r.hotel.hotelId = :hotelId", Room.class)
                .setParameter("hotelId", hotelId).getResultList();
    }
    public List<Room> getAllRoomNumbersForCity(String city){
        return em.createQuery("SELECT r FROM Room r WHERE r.hotel.hotelAddress like :city", Room.class)
                .setParameter("city", city).getResultList();
    }

    public void addRoom(Room room){
        em.merge(room);
    }

    public boolean deleteRoom(int roomNumber, int hotelId){
        if(roomRepository.existsById(new RoomIds(roomNumber, hotelRepository.findById(hotelId).orElse(null)))){
            roomRepository.deleteById(new RoomIds(roomNumber, hotelRepository.findById(hotelId).orElse(null)));
            return true;
        }else{
            return false;
        }
    }


    public List<Room> findRoomsWithParams(Map<String, String> params){
        int hotelId = -1;
        int roomTypeId = -1;
        String city = null;
        int floor = -1;
        int people = -1;
        if(params.containsKey("floor")){
            floor = Integer.parseInt(params.get("floor"));
        }
        if(params.containsKey("hotelId")){
            hotelId = Integer.parseInt(params.get("hotelId"));
        }
        if(params.containsKey("people")){
            people = Integer.parseInt(params.get("people"));
        }
        if(params.containsKey("city")){
            city = params.get("city");
            hotelId = -1;
        }
        if(params.containsKey("roomTypeId")){
            roomTypeId = Integer.parseInt((String) params.get("roomTypeId"));
        }
            List<Room> rooms = em.createQuery("select r" +
                    " from Room r where "+
                    "(:hotelId = -1 or r.hotel.hotelId = :hotelId)" +
                    " and (:roomTypeId = -1 or r.roomType.roomTypeId = :roomTypeId)" +
                    " and r.roomType.roomTypePeople > :people" +
                    " and r.roomFloor > :floor" +
                    " and (:city is null or r.hotel.hotelAddress like :city)", Room.class).
                    setParameter("people", people).setParameter("floor", floor).
                    setParameter("roomTypeId", roomTypeId).setParameter("hotelId", hotelId).
                    setParameter("city", city).getResultList();
            return rooms;
    }
}
