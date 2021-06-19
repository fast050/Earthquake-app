package com.example.earthquake3.ui;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.earthquake3.R;
import com.example.earthquake3.adapter.EarthQuakeReportAdapter;
import com.example.earthquake3.adapter.NewsAdapter;
import com.example.earthquake3.adapter.OnButtonClickListener;
import com.example.earthquake3.util.Constants;
import com.example.earthquake3.viewmodel.EarthQuakeViewModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements OnButtonClickListener {
    @Override
    public void OnButtonClick() {

        makeSearch();

    }

    private EarthQuakeViewModel earthQuakeViewModel;
    private EarthQuakeReportAdapter adapter;
    private ConstraintLayout editLayout;
    private RecyclerView searchRecyclerView;
    private ProgressBar progressBar;
    private EditText editYear, editMagnitude;
    private Button searchButton;
    private int year, magnitude;


    public SearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editLayout = view.findViewById(R.id.edit_query_search_layout);
        editYear = view.findViewById(R.id.editTextYear);
        editMagnitude = view.findViewById(R.id.editTextMagnitude);
        searchButton = view.findViewById(R.id.searchButton);
        progressBar = view.findViewById(R.id.progressBarSearchRecyclerView);
        setRecyclerView(view);
        return view;
    }

    private void setRecyclerView(View view) {


        searchRecyclerView = view.findViewById(R.id.recyclerViewSearch);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new EarthQuakeReportAdapter(getActivity(), this);
        searchRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        earthQuakeViewModel = new ViewModelProvider(requireActivity()).get(EarthQuakeViewModel.class);

        makeSearch();
    }

    private void makeSearch() {
        editLayout.setVisibility(View.VISIBLE);
        searchRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        searchButton.setOnClickListener(v -> {

            if ("".equals(editYear.getText().toString()) && "".equals(editMagnitude.getText().toString())) {
                Toast.makeText(getActivity(), "please enter the year and the magnitude", Toast.LENGTH_SHORT).show();
                return;
            }


            if (editYear.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "please enter the year", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editMagnitude.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "please enter the magnitude", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!"".equals(editYear.getText().toString()) && !"".equals(editMagnitude.getText().toString())) {
                editLayout.setVisibility(View.GONE);
                year = Integer.parseInt(editYear.getText().toString());
                magnitude = Integer.parseInt(editMagnitude.getText().toString());

                messageToUserErrorSearch(year,magnitude);

                makeEarthQuakeSearchCall(year, magnitude);
                StateOfCall();
            }


        });
    }

    private void messageToUserErrorSearch(int year, int magnitude) {
        ArrayList<String> arrayList = new ArrayList<>();


        if (!(year >= 1970 && year <= Constants.CurrentYear) && !(magnitude >= 1 && magnitude <= 10)) {

            arrayList.add("year out of range ,1970 -"+Constants.CurrentYear);
            arrayList.add("magnitude of of range,1 - 10");

        } else if (!(year >= 1970 && year <= Constants.CurrentYear)) {
             arrayList.add("year out of range ,1970 -"+Constants.CurrentYear);
             arrayList.add("");
        } else if (!(magnitude >= 1 && magnitude <= 10)) {
            arrayList.add("");
            arrayList.add("magnitude of of range,1 - 10");
        }
        else
            {
                arrayList.add("");
                arrayList.add("");
            }

        if(!arrayList.get(0).equals(""))
        adapter.setMessageToUser(arrayList.get(0)+", "+arrayList.get(1));
        else
            adapter.setMessageToUser(arrayList.get(1));
    }

    private void StateOfCall() {
        earthQuakeViewModel.getLoadingState().observe(getViewLifecycleOwner(), new Observer<Constants.LoadingBarState>() {
            @Override
            public void onChanged(Constants.LoadingBarState loadingBarState) {

                adapter.setState(loadingBarState);

                if (loadingBarState == Constants.LoadingBarState.LOADING) {
                    progressBar.setVisibility(View.VISIBLE);
                    searchRecyclerView.setVisibility(View.GONE);

                } else {

                    progressBar.setVisibility(View.GONE);
                    searchRecyclerView.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void makeEarthQuakeSearchCall(int year, int magnitude) {
        earthQuakeViewModel.getSearchEarthQuakeDataList(year, magnitude).observe(getViewLifecycleOwner(), resultList ->
        {
            adapter.setListEarthQuake(resultList);
        });
    }
}