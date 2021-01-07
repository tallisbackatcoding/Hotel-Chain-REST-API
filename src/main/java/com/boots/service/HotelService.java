package com.boots.service;

import com.boots.entity.Hotel;
import com.boots.entity.Room;
import com.boots.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @PersistenceContext
    private EntityManager em;

    public List<Hotel> getAllHotels(){
        List<Hotel> hotels = new ArrayList<>();
        hotelRepository.findAll().forEach(hotels::add);
        return hotels;
    }

    public List<Hotel> getHotelsByCity(String city){
        return em.createQuery("select h from Hotel h where h.hotelAddress like :city", Hotel.class).
                setParameter("city", city).getResultList();
    }

    public Hotel getHotelById(int hotelId){
        return hotelRepository.findById(hotelId).orElse(null);
    }
    public void addHotel(Hotel hotel){
        em.merge(hotel);
    }
    public boolean removeHotelById(int hotelId){
        if(hotelRepository.existsById(hotelId)){
            hotelRepository.deleteById(hotelId);
            return true;
        }else{
            return false;
        }
    }
    public void addPhone(int hotelId, String phone){
        Hotel hotel = getHotelById(hotelId);
        hotel.addHotelPhone(phone);
        hotelRepository.save(hotel);
    }

    public Hotel findHotelByNameAndCity(String name, String city){
        try{
            Hotel hotel = em.createQuery("select h from Hotel h where " +
                    "h.hotelAddress like :city and " +
                    "h.hotelName like :name", Hotel.class).
                    setParameter("name", name).
                    setParameter("city", city).getSingleResult();
            return hotel;
        }catch (NoResultException e){
            return null;
        }
    }
    public void unCleanAllRoomsForHotel(int hotelId){
        Hotel hotel = getHotelById(hotelId);
        List<Room> rooms = hotel.getRooms();
        for(int i = 0; i < rooms.size(); i++){
            rooms.get(i).setClean(false);
        }
        addHotel(hotel);
    }
}
