package com.boots.repository;

import com.boots.entity.Hotel;
import com.boots.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

}
