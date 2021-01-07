package com.boots.mymodel;


import com.boots.entity.Room;
import com.boots.entity.RoomType;

import java.util.Date;
import java.util.Map;

public class RoomModel {
    private int hotelId;
    private RoomType roomType;
    private Map<String, Integer> discounts;
    private int finalPrice;
    private int originalPrice;
    public RoomModel(){

    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public RoomModel(RoomType roomType, Map<String, Object> prices){
        this.roomType = roomType;
        this.hotelId = roomType.getHotelId();
        try{
            this.discounts = (Map<String, Integer>)prices.get("discounts");
        }catch (Exception e){
            System.out.println("Couldn't convert Object into Map in RoomModel constructor");
        }
    }


    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Map<String, Integer> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Map<String, Integer> discounts) {
        this.discounts = discounts;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
