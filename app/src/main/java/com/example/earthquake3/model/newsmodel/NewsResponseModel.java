package com.example.earthquake3.model.newsmodel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class NewsResponseModel {

    @SerializedName("articles")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }
}
