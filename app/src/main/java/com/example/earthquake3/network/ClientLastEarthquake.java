package com.example.earthquake3.network;

import com.example.earthquake3.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientLastEarthquake {
    private static ApiService instance;

    static public  ApiService getRetrofitClient ()
    {
        if(instance==null)
        {

            instance=new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.Client_BaseURL_Last)
                    .build().create(ApiService.class);

            return instance;
        }

        return instance;
    }
}
