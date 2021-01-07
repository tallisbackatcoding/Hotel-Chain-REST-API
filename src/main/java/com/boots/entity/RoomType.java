package com.boots.entity;

import com.boots.idclasses.RoomTypeIds;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;


@Entity
@IdClass(RoomTypeIds.class)
@Table(name = "t_room_type")
public class RoomType implements Persistable<RoomTypeIds> {
    @JsonIgnore
    @Transient
    private boolean isNew = false;

    @Id
    private int roomTypeId;

    @Id
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;


    @ElementCollection
    @CollectionTable(
            name = "room_type_features",
            joinColumns = {@JoinColumn(name = "room_type_id"),
                           @JoinColumn(name="hotel_id")}

    )
    private List<String> roomTypeFeatures;

    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;

    private int roomTypePeople;

    private int roomTypeSize;

    private int roomTypePrice;

    public int getRoomTypePrice() {
        return roomTypePrice;
    }

    public void setRoomTypePrice(int roomTypePrice) {
        this.roomTypePrice = roomTypePrice;
    }

    public int getRoomTypePeople() {
        return roomTypePeople;
    }

    public void setRoomTypePeople(int roomTypePeople) {
        this.roomTypePeople = roomTypePeople;
    }

    public int getRoomTypeSize() {
        return roomTypeSize;
    }

    public void setRoomTypeSize(int roomTypeSize) {
        this.roomTypeSize = roomTypeSize;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public List<String> getFeatures() {
        return roomTypeFeatures;
    }

    public void setFeatures(List<String> roomTypeFeatures) {
        this.roomTypeFeatures = roomTypeFeatures;
    }

    public RoomType(){

    }
    public int getHotelId() {
        return hotel.getHotelId();
    }

    public void setHotelId(int hotelId) {
        this.hotel.setHotelId(hotelId);
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @JsonIgnore
    @Override
    public RoomTypeIds getId() {
        return new RoomTypeIds(roomTypeId, hotel);
    }
}
