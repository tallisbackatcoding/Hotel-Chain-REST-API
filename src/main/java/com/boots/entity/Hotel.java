package com.boots.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "t_hotel")

public class Hotel implements Persistable<Integer> {

    @Id @GeneratedValue
    private int hotelId;
    private String hotelAddress;
    private String hotelName;
    @ElementCollection
    @CollectionTable(
            name = "phones",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
    private List<String> hotelPhones;
    @OneToMany(mappedBy = "hotel", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<RoomType> roomTypes;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private List<Employee> employees;

    @JsonIgnore
    public List<Room> getRooms() {
        return rooms;
    }

    @OneToMany(mappedBy = "hotel")
    private List<Season> seasons;

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public Hotel(){

    }
    public Hotel(int hotelId){
        this.hotelId = hotelId;
    }

    @JsonIgnore
    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<String> getHotelPhones() {
        return hotelPhones;
    }

    public void addHotelPhone(String phone) {
        this.hotelPhones.add(phone);
    }

    public void setHotelPhones(List<String> hotelPhones) {
        this.hotelPhones = hotelPhones;
    }

    @Override
    public Integer getId() {
        return hotelId;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
