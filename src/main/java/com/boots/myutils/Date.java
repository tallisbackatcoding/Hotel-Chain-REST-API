package com.boots.myutils;

public class Date {
    private int year;
    private int month;
    private int day;

    public int compareDate(Date d1, Date d2){
        if(d1.getYear() < d2.getYear()){
            return -1;
        }else if(d1.getYear() > d2.getYear()){
            return 1;
        }
        if(d1.getMonth() < d2.getMonth()){
            return -1;
        }else if(d1.getMonth() > d2.getMonth()){
            return 1;
        }
        if(d1.getDay() < d2.getDay()){
            return -1;
        }else if(d1.getDay() > d2.getDay()){
            return 1;
        }
        return 0;
    }

    public Date(){

    }
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
