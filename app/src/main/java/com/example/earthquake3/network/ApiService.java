package com.example.earthquake3.network;

import com.example.earthquake3.model.items.EarthQuakeReportModel;
import com.example.earthquake3.model.newsmodel.NewsResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


  // https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-12-30&minmagnitude=7
    @GET("query")
    Call<EarthQuakeReportModel> getEarthQuakeReportCall(@Query("format") String format,
                                                        @Query("starttime") String startDate,
                                                        @Query("endtime") String endDate,
                                                        @Query("minmagnitude") int magnitude);

    @GET("summary/all_day.geojson")
    Call<EarthQuakeReportModel> getLastEarthQuakeCall();


    //https://newsapi.org/v2/everything?q=earthquake&apiKey=2f9cbeb0250a4416be8506c75ebadc6e
    @GET("everything")
    Call<NewsResponseModel> getEarthQuakeNewsCall(@Query("q") String SearchAbout, @Query("apiKey") String apiKey);



}
