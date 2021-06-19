package com.example.earthquake3.model.items;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Properties {
    @SerializedName("mag")
    private Double mag;

    @SerializedName("place")
    private String place;

    @SerializedName("time")
    private Long date;

    public Long getDate() {
        return date;
    }

    public Double getMag() {
        return mag;
    }

    public String getPlace() {
        return place;
    }


    public void setMag(Double mag) {
        this.mag = mag;
    }

    public void setPlace(String place) {
        this.place = place;
    }


    @Override
    public String toString() {
        return "Properties{" +
                "mag=" + mag +
                ", place='" + place + '\'' +
                ", date='" + FormatDate(date) + '\'' +
                '}';
    }

    //to convert time to readable from milliSeconds
    private  String FormatDate(long timeInMilliSeconds)
    {
        //to make object form the
        Date dateObject = new Date(timeInMilliSeconds);
        //the time form we need or want
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");


        return dateFormatter.format(dateObject);
    }


}
