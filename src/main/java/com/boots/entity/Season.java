package com.boots.entity;

import com.boots.idclasses.SeasonIds;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@IdClass(SeasonIds.class)
@Table(name = "t_season")
public class Season{

    @Id @GeneratedValue
    private int seasonId;
    private Date seasonInDate;
    private Date seasonOutDate;
    private int seasonDiscount;

    @Id
    @ManyToOne
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    public int getHotel() {
        return hotel.getHotelId();
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public Date getSeasonInDate() {
        return seasonInDate;
    }

    public void setSeasonInDate(Date seasonInDate) {
        this.seasonInDate = seasonInDate;
    }

    public Date getSeasonOutDate() {
        return seasonOutDate;
    }

    public void setSeasonOutDate(Date seasonOutDate) {
        this.seasonOutDate = seasonOutDate;
    }

    public int getSeasonDiscount() {
        return seasonDiscount;
    }

    public void setSeasonDiscount(int seasonDiscount) {
        this.seasonDiscount = seasonDiscount;
    }

    public Season() {
    }
}
