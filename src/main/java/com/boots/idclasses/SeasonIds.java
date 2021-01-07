package com.boots.idclasses;
import com.boots.entity.Hotel;
import java.io.Serializable;

public class SeasonIds implements Serializable {
    private int seasonId;
    private Hotel hotel;

    public SeasonIds() {
    }

    public SeasonIds(Integer seasonId, Hotel hotel) {
        this.seasonId = seasonId;
        this.hotel = hotel;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
