package com.example.earthquake3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.earthquake3.model.items.Feature;
import com.example.earthquake3.model.newsmodel.Article;
import com.example.earthquake3.repository.EarthQuakeRepository;
import com.example.earthquake3.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakeViewModel extends ViewModel {

    private final EarthQuakeRepository earthQuakeRepository;
    private final MutableLiveData<ArrayList<String>> SearchErrorMessage;


    public EarthQuakeViewModel() {
        earthQuakeRepository = EarthQuakeRepository.getInstance();
        SearchErrorMessage=new MutableLiveData<>();
    }

    public LiveData<List<Feature>> getSearchEarthQuakeDataList(int Year,int magnitude) {
        return  earthQuakeRepository.getSearchEarthQuakeList(Year,magnitude);
    }

    public LiveData<List<Feature>> getLastEarthQuakeDataList() {
        return earthQuakeRepository.getLastEarthQuakeList();
    }

    public LiveData<List<Article>> getEarthQuakeDataNewsList() {
        return earthQuakeRepository.getEarthQuakeNewsList();
    }

    public MutableLiveData<ArrayList<String>> getSearchErrorMessage() {
        return SearchErrorMessage;
    }


    public LiveData<Constants.LoadingBarState> getLoadingState() {
        return earthQuakeRepository.getLoadingStateBar();
    }

    public LiveData<String> getMessageToUser() {
        return earthQuakeRepository.getMessageToUsers();
    }
}
