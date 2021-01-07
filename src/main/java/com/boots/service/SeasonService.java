package com.boots.service;

import com.boots.entity.Hotel;
import com.boots.entity.Season;
import com.boots.repository.HotelRepository;
import com.boots.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;
    @PersistenceContext
    private EntityManager em;

    public List<Season> getAllSeasons(){
        List<Season> seasons = new ArrayList<>();
        seasonRepository.findAll().forEach(seasons::add);
        return seasons;
    }

    public Season findSeasonByDate(Date date, int hotelId){
        try{
            List<Season> seasons = em.createQuery("select s" +
                    " from Season s where s.seasonInDate < :date and :date < s.seasonOutDate and " +
                    "s.hotel.hotelId = :hotelId", Season.class).
                    setParameter("date", date).setParameter("hotelId", hotelId).getResultList();
            return seasons.get(0);
        }catch (Exception e){
            return null;
        }

    }
}
