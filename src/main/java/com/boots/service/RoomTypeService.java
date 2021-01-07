package com.boots.service;

import com.boots.entity.RoomType;
import com.boots.idclasses.RoomTypeIds;
import com.boots.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private HotelService hotelService;

    @PersistenceContext
    EntityManager em;

    public List<RoomType> getAllRoomTypes(){
        List<RoomType> roomTypes = new ArrayList<RoomType>();
        roomTypeRepository.findAll().forEach(roomTypes::add);
        return roomTypes;
    }

    public RoomType getRoomType(int roomTypeId, int hotelId){
        System.out.println(roomTypeId + " " + hotelId);
        return roomTypeRepository.findById(new RoomTypeIds(roomTypeId, hotelService.getHotelById(hotelId))).orElse(null);
    }

    public void addRoomType(RoomType roomType){
        em.merge(roomType);
    }
    
    public boolean deleteRoomType(int roomTypeId, int hotelId){
        if(getRoomType(roomTypeId, hotelId) != null){
            roomTypeRepository.deleteById(new RoomTypeIds(roomTypeId, hotelService.getHotelById(hotelId)));
            return true;
        }else{
            return false;
        }
    }

    public List<RoomType> getAllRoomsTypesForHotel(int hotelId) {
        return em.createQuery("SELECT r FROM RoomType r WHERE r.hotel.hotelId = :hotelId", RoomType.class)
                .setParameter("hotelId", hotelId).getResultList();
    }
}
