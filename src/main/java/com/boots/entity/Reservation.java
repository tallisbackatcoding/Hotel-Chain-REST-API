package com.boots.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="t_reservation")
public class Reservation implements Serializable{


    @Id @GeneratedValue
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @Transient
    SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");


    public Long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }
    //private int userId;

    private Date inDate;

    private Date outDate;

    private Date paidDate;

    private int cost;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JoinColumn(name= "room_number")
    private Room room;

    public Reservation() {

    }
    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    /*
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }*/

    public int getRoom() {
        return room.getRoomNumber();
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @JsonIgnore
    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }
    @JsonIgnore
    public Date getOutDate() {
        return outDate;
    }

    public String getInDateStr() {
        return DateFor.format(inDate);
    }

    public String getHotelName(){
        return room.getHotel().getHotelName();
    }
    public String getHotelAddress(){
        return room.getHotel().getHotelAddress();
    }

    public String getOutDateStr() {
        return DateFor.format(outDate);
    }
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getPaidDateStr() {
        return DateFor.format(paidDate);
    }


    public void paidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Reservation(User user, /*int userId, */Room room,
                       Date inDate, Date outDate,
                       Date paidDate) {
        //this.userId = userId;
        this.user = user;
        this.room = room;
        this.inDate = inDate;
        this.outDate = outDate;
        this.paidDate = paidDate;
    }

}
