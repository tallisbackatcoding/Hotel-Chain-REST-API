package com.boots.idclasses;

import com.boots.entity.Hotel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class EmployeeIds implements Serializable {
    private Integer id;
    private Hotel hotel;

    public EmployeeIds() {
    }

    public EmployeeIds(Integer id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
