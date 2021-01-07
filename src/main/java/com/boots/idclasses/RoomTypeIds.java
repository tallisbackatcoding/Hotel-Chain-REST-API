package com.boots.idclasses;

import com.boots.entity.Hotel;
import com.boots.entity.Room;

import java.io.Serializable;

public class RoomTypeIds implements Serializable{
    private int roomTypeId;
    private Hotel hotel;

    public RoomTypeIds() {

    }

    public RoomTypeIds(int roomTypeId, Hotel hotel) {
        this.roomTypeId = roomTypeId;
        this.hotel = hotel;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}