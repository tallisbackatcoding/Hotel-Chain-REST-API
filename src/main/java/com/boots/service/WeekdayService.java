package com.boots.service;

import com.boots.entity.Hotel;
import com.boots.entity.Weekday;
import com.boots.repository.HotelRepository;
import com.boots.repository.WeekdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeekdayService {

    @Autowired
    private WeekdayRepository weekdayRepository;

    public List<Weekday> getAllWeekdays(){
        List<Weekday> weekdays = new ArrayList<>();
        weekdayRepository.findAll().forEach(weekdays::add);
        return weekdays;
    }

    public int getWeekDayDiscountById(int id){
        System.out.println("week day is: " + id);
        return weekdayRepository.findById(id).get().getWeekDayDiscount();
    }
}
