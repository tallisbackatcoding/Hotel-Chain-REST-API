package com.boots.entity;

import com.boots.idclasses.RoomIds;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;

/*
class roomIds implements Serializable{
    private int roomNumber;
    private Hotel hotel;
}*/


@Entity
@IdClass(RoomIds.class)
@Table(name="t_room",
        schema="my_db")
public class Room implements Persistable<RoomIds> {
    @Transient
    private boolean isNew = false;
    @Id
    @Column(name = "room_number")
    private int roomNumber;
    @Column(name="room_floor")
    private int roomFloor;


    private boolean isClean = true;

    @Id
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumns({@JoinColumn( name = "hotel_id", insertable = false, updatable = false),
            @JoinColumn(name = "room_type_id", insertable = false, updatable = false)})
    private RoomType roomType;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservationList;

    @Override
    public boolean isNew() {
        return isNew;
    }


    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
    @Override
    public RoomIds getId() {
        return new RoomIds(roomNumber, hotel);
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @JsonIgnore
    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @JsonIgnore
    public RoomType getRoomType() {
        return roomType;
    }

    public Integer getRoomTypeId(){
        if(roomType == null){
            return null;
        }
        return roomType.getRoomTypeId();
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Room() {

    }

    public Room(int roomNumber, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.hotel = hotel;
    }
    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Hotel getHotel() {

        return hotel;
    }

    public int getHotelId() {

        return hotel.getHotelId();
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    //@Id
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

}
