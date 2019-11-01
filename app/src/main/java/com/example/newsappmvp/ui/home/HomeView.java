package com.example.newsappmvp.ui.home;

import com.example.newsappmvp.model.headLines.Article;

import java.util.List;

public interface HomeView {
    void getDataFromApi(List<Article> article,boolean isCountry);
    void onFailureData(String failureMessage);
}
