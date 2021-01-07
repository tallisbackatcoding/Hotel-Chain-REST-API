package com.boots.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_weekday")
public class Weekday {
    @Id
    private int weekDay;
    private int weekDayDiscount;

    public Weekday() {

    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getWeekDayDiscount() {
        return weekDayDiscount;
    }

    public void setWeekDayDiscount(int weekDayDiscount) {
        this.weekDayDiscount = weekDayDiscount;
    }
}
