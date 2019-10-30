package com.example.newsappmvp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.newsappmvp.R;
import com.example.newsappmvp.model.headLines.Article;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView {

    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenter(this);
        init();
    }

    private void init() {

presenter.getNews();
    }



    @Override
    public void getDataFromApi(List<Article> article) {
        Toast.makeText(this, ""+article.get(0).getAuthor(), Toast.LENGTH_SHORT).show();
    }
}
