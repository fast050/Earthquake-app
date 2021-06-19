package com.example.earthquake3.model.items;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarthQuakeReportModel {
    @SerializedName("features")
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }


    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}

