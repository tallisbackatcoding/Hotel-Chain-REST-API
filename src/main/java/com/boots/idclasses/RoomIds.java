package com.boots.idclasses;

import com.boots.entity.Hotel;

import java.io.Serializable;

public class RoomIds implements Serializable {
    private int roomNumber;
    private Hotel hotel;

    public RoomIds() {

    }

    public RoomIds(int roomNumber, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.hotel = hotel;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

}