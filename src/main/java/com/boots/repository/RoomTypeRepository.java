package com.boots.repository;

import com.boots.entity.RoomType;
import com.boots.idclasses.RoomTypeIds;
import org.springframework.data.repository.CrudRepository;

public interface RoomTypeRepository extends CrudRepository<RoomType, RoomTypeIds> {

}
