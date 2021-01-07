package com.boots.repository;

import com.boots.entity.Hotel;
import com.boots.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {

}
