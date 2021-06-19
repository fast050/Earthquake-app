package com.example.earthquake3.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.earthquake3.model.items.EarthQuakeReportModel;
import com.example.earthquake3.model.items.Feature;
import com.example.earthquake3.model.newsmodel.Article;
import com.example.earthquake3.model.newsmodel.NewsResponseModel;
import com.example.earthquake3.network.ApiService;
import com.example.earthquake3.network.ClientLastEarthquake;
import com.example.earthquake3.network.ClientNews;
import com.example.earthquake3.network.ClientSearchEarthquake;
import com.example.earthquake3.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EarthQuakeRepository {

    private static ApiService apiService_SearchEarthQuake;
    private static ApiService apiService_news;
    private static ApiService apiService_LastEarthQuake;
    private static EarthQuakeRepository instance;
    private final MutableLiveData<Constants.LoadingBarState> LoadingStateBar;
    private final MutableLiveData<Boolean> IsCallSuccess;
    private final MutableLiveData<List<Feature>> earthQuakeListLiveData;
    private final MutableLiveData<List<Article>> earthQuakeNewsListLiveData;
    private final MutableLiveData<List<Feature>> earthQuakeLastListLiveData;
    private final MutableLiveData<String> messageToUsers ;

    public static EarthQuakeRepository getInstance() {

        if (instance == null) {

            instance = new EarthQuakeRepository();
        }
        return instance;
    }

    public EarthQuakeRepository() {
        apiService_SearchEarthQuake = ClientSearchEarthquake.getRetrofitClient();
        apiService_news = ClientNews.getRetrofitClient();
        apiService_LastEarthQuake = ClientLastEarthquake.getRetrofitClient();
        earthQuakeLastListLiveData = new MutableLiveData<>();
        earthQuakeListLiveData = new MutableLiveData<>();
        earthQuakeNewsListLiveData = new MutableLiveData<>();
        messageToUsers = new MutableLiveData<>();
        IsCallSuccess=new MutableLiveData<>();
        LoadingStateBar = new MutableLiveData<>();

    }

    public MutableLiveData<List<Feature>> getSearchEarthQuakeList(int Year ,int magnitude) {

        LoadingStateBar.setValue(Constants.LoadingBarState.LOADING);

        apiService_SearchEarthQuake.getEarthQuakeReportCall(Constants.URL_Format,Year+"-01-01" , Constants.FormatYearSearchApiCall(Year),magnitude).enqueue(new Callback<EarthQuakeReportModel>() {
            @Override
            public void onResponse(Call<EarthQuakeReportModel> call, Response<EarthQuakeReportModel> response) {

                if (response.isSuccessful() && !(response.body().getFeatures().isEmpty())) {
                    if (response.code() == 204)  //204 is empty body
                    {
                        earthQuakeListLiveData.setValue(null);
                        LoadingStateBar.setValue(Constants.LoadingBarState.EMPTY);
                        messageToUsers.setValue("There no Data here");
                        IsCallSuccess.setValue(false);
                    } else {
                        earthQuakeListLiveData.setValue(response.body().getFeatures());
                        LoadingStateBar.setValue(Constants.LoadingBarState.SUCCESS);
                        IsCallSuccess.setValue(true);
                    }

                } else {
                    earthQuakeListLiveData.setValue(null);
                    LoadingStateBar.setValue(Constants.LoadingBarState.FAILED);
                    messageToUsers.setValue("Something Wrong With Server");
                    IsCallSuccess.setValue(false);
                }


            }

            @Override
            public void onFailure(Call<EarthQuakeReportModel> call, Throwable t) {
                earthQuakeListLiveData.setValue(null);
                LoadingStateBar.setValue(Constants.LoadingBarState.NO_INTERNET);
                messageToUsers.setValue("Check your Connection");
                IsCallSuccess.setValue(false);
            }
        });

        return earthQuakeListLiveData;
    }

    public MutableLiveData<List<Article>> getEarthQuakeNewsList() {

        LoadingStateBar.setValue(Constants.LoadingBarState.LOADING);


        //https://newsapi.org/v2/everything?q=earthquake&apiKey=2f9cbeb0250a4416be8506c75ebadc6e

        apiService_news.getEarthQuakeNewsCall("earthquake", "2f9cbeb0250a4416be8506c75ebadc6e").enqueue(new Callback<NewsResponseModel>() {
            @Override
            public void onResponse(Call<NewsResponseModel> call, Response<NewsResponseModel> response) {

                if (response.isSuccessful() && !(response.body().getArticles().isEmpty())) {

                    if (response.code() == 204)  //204 is empty body
                    {
                        earthQuakeNewsListLiveData.setValue(null);
                        LoadingStateBar.setValue(Constants.LoadingBarState.EMPTY);
                        messageToUsers.setValue("There no Data here");
                        IsCallSuccess.setValue(false);
                    } else {
                        earthQuakeNewsListLiveData.setValue(response.body().getArticles());
                        LoadingStateBar.setValue(Constants.LoadingBarState.SUCCESS);
                        IsCallSuccess.setValue(true);
                    }

                } else {
                    earthQuakeNewsListLiveData.setValue(null);
                    LoadingStateBar.setValue(Constants.LoadingBarState.FAILED);
                    messageToUsers.setValue("Something Wrong With Server");
                    IsCallSuccess.setValue(false);
                }


            }

            @Override
            public void onFailure(Call<NewsResponseModel> call, Throwable t) {
                System.out.println(t.getMessage());
                earthQuakeNewsListLiveData.setValue(null);
                LoadingStateBar.setValue(Constants.LoadingBarState.NO_INTERNET);
                messageToUsers.setValue("Check your Connection");
                IsCallSuccess.setValue(false);
            }
        });

        return earthQuakeNewsListLiveData;
    }

    public MutableLiveData<List<Feature>> getLastEarthQuakeList() {

        LoadingStateBar.setValue(Constants.LoadingBarState.LOADING);


        apiService_LastEarthQuake.getLastEarthQuakeCall().enqueue(new Callback<EarthQuakeReportModel>() {
            @Override
            public void onResponse(Call<EarthQuakeReportModel> call, Response<EarthQuakeReportModel> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 204)  //204 is empty body
                    {
                        earthQuakeLastListLiveData.setValue(response.body().getFeatures());
                        LoadingStateBar.setValue(Constants.LoadingBarState.EMPTY);
                        messageToUsers.setValue("There no Data here");
                        IsCallSuccess.setValue(false);
                    } else {

                        earthQuakeLastListLiveData.setValue(response.body().getFeatures());
                        LoadingStateBar.setValue(Constants.LoadingBarState.SUCCESS);
                        IsCallSuccess.setValue(true);
                    }

                } else {
                    earthQuakeLastListLiveData.setValue(null);
                    LoadingStateBar.setValue(Constants.LoadingBarState.FAILED);
                    messageToUsers.setValue("Something Wrong With Server");
                    IsCallSuccess.setValue(false);
                }


            }

            @Override
            public void onFailure(Call<EarthQuakeReportModel> call, Throwable t) {
                earthQuakeLastListLiveData.setValue(null);
                LoadingStateBar.setValue(Constants.LoadingBarState.NO_INTERNET);
                messageToUsers.setValue("Check your Connection");
                IsCallSuccess.setValue(false);
            }
        });

        return earthQuakeLastListLiveData;
    }


    public MutableLiveData<Constants.LoadingBarState> getLoadingStateBar() {
        return LoadingStateBar;
    }

    public MutableLiveData<String> getMessageToUsers() {
        return messageToUsers;
    }

    public MutableLiveData<Boolean> getIsCallSuccess() {
        return IsCallSuccess;
    }
}
