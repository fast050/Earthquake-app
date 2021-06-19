package com.example.earthquake3.network;

import com.example.earthquake3.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSearchEarthquake {

    private static ApiService instance;

    static public  ApiService getRetrofitClient ()
    {
        if(instance==null)
        {

            instance=new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.Client_BaseURL_Search)
                    .build().create(ApiService.class);

            return instance;
        }

        return instance;
    }

}
