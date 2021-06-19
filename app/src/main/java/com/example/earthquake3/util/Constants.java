package com.example.earthquake3.util;

import java.util.Calendar;

public class Constants {


    public static final String URL_Format="geojson";
    public static final String Client_BaseURL_Search="https://earthquake.usgs.gov/fdsnws/event/1/";
    public static final String Client_BaseURL_News="https://newsapi.org/v2/";
    public static final String Client_BaseURL_Last="https://earthquake.usgs.gov/earthquakes/feed/v1.0/";
    public static final int CurrentYear=Calendar.getInstance().get(Calendar.YEAR);

    public enum LoadingBarState{LOADING,SUCCESS,FAILED, NO_INTERNET,EMPTY}

    public static String FormatYearSearchApiCall(int year)
        {
            Calendar c = Calendar.getInstance();
            int currentYear = CurrentYear;
            if (year==currentYear)
            {
                int monthEnd = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                return currentYear+"-"+monthEnd+"-"+day;
            }

            return year+"-12-30";
        }



}
