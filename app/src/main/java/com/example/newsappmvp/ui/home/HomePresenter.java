package com.example.newsappmvp.ui.home;

import android.util.Log;
import android.view.View;

import com.example.newsappmvp.ApiCall.APIInterface;
import com.example.newsappmvp.ApiCall.ApiClient;
import com.example.newsappmvp.model.headLines.Article;
import com.example.newsappmvp.model.headLines.ResponseHeadLines;
import com.example.newsappmvp.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HomePresenter {

    private HomeView view;
    private int isNews;
    private String country = "us", sources = "";
    private List<Article> articleList;

    HomePresenter(HomeView view) {
        this.view = view;
    }

    void getNews() {


        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseHeadLines> call = apiService.getNews(country, Constants.KEY);
        callEnqueue(call);
    }


    private void callEnqueue(Call<ResponseHeadLines> call) {
        call.enqueue(new Callback<ResponseHeadLines>() {
            @Override
            public void onResponse(Call<ResponseHeadLines> call, Response<ResponseHeadLines> response) {
                if (response.body().getStatus().equals("ok")) {
                    articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        view.getDataFromApi(articleList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseHeadLines> call, Throwable t) {
                Log.e("out", t.toString());

            }
        });
    }
}
