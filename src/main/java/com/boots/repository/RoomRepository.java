package com.boots.repository;

import com.boots.entity.Room;
import com.boots.idclasses.RoomIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, RoomIds> {
    /*
    //getAllRooms()
    if (entityInformation.isNew(entity)) {
        em.persist(entity);
        return entity;
    } else {
        return em.merge(entity);
    }*/
}
