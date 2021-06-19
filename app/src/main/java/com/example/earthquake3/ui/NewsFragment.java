package com.example.earthquake3.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.earthquake3.R;

import com.example.earthquake3.adapter.NewsAdapter;

import com.example.earthquake3.adapter.OnButtonClickListener;

import com.example.earthquake3.model.newsmodel.Article;
import com.example.earthquake3.util.Constants;
import com.example.earthquake3.viewmodel.EarthQuakeViewModel;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnButtonClickListener {


    @Override
    public void OnButtonClick() {
        makeNewsCall();
    }

    private static final String TAG = "TagD";
    private EarthQuakeViewModel earthQuakeViewModel;
    private NewsAdapter newsAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;


    public NewsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = view.findViewById(R.id.progressBarMainRecycler);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        setRecyclerView(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        earthQuakeViewModel = new ViewModelProvider(requireActivity()).get(EarthQuakeViewModel.class);

        makeNewsCall();
        StateOfCall();




    }

    private void setRecyclerView(View view) {


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEQ_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newsAdapter = new NewsAdapter(getActivity(), this);
        recyclerView.setAdapter(newsAdapter);

    }

    private void StateOfCall() {
        earthQuakeViewModel.getLoadingState().observe(getViewLifecycleOwner(), new Observer<Constants.LoadingBarState>() {
            @Override
            public void onChanged(Constants.LoadingBarState loadingBarState) {

                newsAdapter.setState(loadingBarState);

                if (loadingBarState == Constants.LoadingBarState.LOADING) {
                    progressBar.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);

                } else {

                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);

                }
            }
        });
    }


    private void makeNewsCall() {
        earthQuakeViewModel.getEarthQuakeDataNewsList()
                .observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
                    @Override
                    public void onChanged(List<Article> articles) {
                        newsAdapter.setArticleList(articles);
                    }
                });


        // Message to user
        earthQuakeViewModel.getMessageToUser().observe(getViewLifecycleOwner(), s -> {
            newsAdapter.setMessageToUser(s);
        });

    }


    @Override
    public void onRefresh() {


        makeNewsCall();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 1000);


        earthQuakeViewModel.getSearchErrorMessage().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                Log.d(TAG, "onChanged: first place : "+strings.get(0) +" , second place :"+strings.get(1));
            }
        });
    }

}
