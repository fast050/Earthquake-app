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
import android.widget.TextView;
import android.widget.Toast;

import com.example.earthquake3.R;
import com.example.earthquake3.adapter.EarthQuakeReportAdapter;
import com.example.earthquake3.adapter.OnButtonClickListener;
import com.example.earthquake3.model.items.EarthQuakeReportModel;
import com.example.earthquake3.model.items.Feature;
import com.example.earthquake3.network.ClientLastEarthquake;
import com.example.earthquake3.repository.EarthQuakeRepository;
import com.example.earthquake3.util.Constants;
import com.example.earthquake3.viewmodel.EarthQuakeViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LastestEarthQuakeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnButtonClickListener {

    @Override
    public void OnButtonClick() {

      makeCallEarthQuakeData();

    }

    private EarthQuakeViewModel earthQuakeViewModel;
    private EarthQuakeReportAdapter earthQuakeReportAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;


    public LastestEarthQuakeFragment() {
        // Required empty public constructor
    }

    public static LastestEarthQuakeFragment newInstance() {
        LastestEarthQuakeFragment fragment = new LastestEarthQuakeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_lastest_earth_quake, container, false);
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
        earthQuakeViewModel =new ViewModelProvider(requireActivity()).get(EarthQuakeViewModel.class);

        makeCallEarthQuakeData();
        StateOfCall();

    }

    private void setRecyclerView(View view) {


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEQ_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        earthQuakeReportAdapter = new EarthQuakeReportAdapter(getActivity(), this);
        recyclerView.setAdapter(earthQuakeReportAdapter);

    }

    private void StateOfCall() {
        earthQuakeViewModel.getLoadingState().observe(getViewLifecycleOwner(), new Observer<Constants.LoadingBarState>() {
            @Override
            public void onChanged(Constants.LoadingBarState loadingBarState) {

                earthQuakeReportAdapter.setState(loadingBarState);

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



    private void makeCallEarthQuakeData() {
        earthQuakeViewModel.getLastEarthQuakeDataList()
                .observe(getViewLifecycleOwner(), new Observer<List<Feature>>() {
                    @Override
                    public void onChanged(List<Feature> features) {
                        earthQuakeReportAdapter.setListEarthQuake(features);
                    }
                });

        // Message to user
        earthQuakeViewModel.getMessageToUser().observe(getViewLifecycleOwner(), s -> {
            earthQuakeReportAdapter.setMessageToUser(s);
        });

    }


    @Override
    public void onRefresh() {

        makeCallEarthQuakeData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 1000);
    }


}