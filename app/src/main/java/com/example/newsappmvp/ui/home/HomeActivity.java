package com.example.newsappmvp.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsappmvp.R;
import com.example.newsappmvp.model.headLines.Article;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    HomePresenter presenter;
    @BindView(R.id.rec_news)
    RecyclerView recNews;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.progress_news)
    ProgressBar progressNews;
    private RecyclerView.LayoutManager newsLayoutManager;
    private NewsAdapter newsAdapter;

    boolean isCountry;
    String country = "us", sources = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presenter = new HomePresenter(this);
        swipeLayout.setOnRefreshListener(this);
        progressNews.setVisibility(View.VISIBLE);
        presenter.getNews(country);

    }


    @Override
    public void getDataFromApi(List<Article> article, boolean isCountry) {
        progressNews.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);
        initRecycle(article);
        this.isCountry = isCountry;
    }

    @Override
    public void onFailureData(String failureMessage) {
        Toast.makeText(this, "error is " + failureMessage, Toast.LENGTH_SHORT).show();
        progressNews.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);
    }

    private void initRecycle(List<Article> article) {
        newsAdapter = new NewsAdapter(article, this);
        recNews.setLayoutManager(getLayoutManager());
        recNews.setAdapter(newsAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (newsLayoutManager == null) {
            newsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        return newsLayoutManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_filter:
                showFilterDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // this method to show filterDialog and handle all action
    private void showFilterDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_filter);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> dialog.dismiss());
        Button buCancel = dialog.findViewById(R.id.bu_cancel);
        //btn
        buCancel.setOnClickListener(v -> dialog.dismiss());
        Spinner spinnerCountry = dialog.findViewById(R.id.spinner_country);
        Spinner spinnerSources = dialog.findViewById(R.id.spinner_sources);
        Button buFilter = dialog.findViewById(R.id.bu_filter);
        buFilter.setOnClickListener(v -> {

            country = spinnerCountry.getSelectedItem().toString();
            sources = spinnerSources.getSelectedItem().toString();
            if (!country.equals("Select Country")) {
                presenter.getNews(country);
                progressNews.setVisibility(View.VISIBLE);
                dialog.dismiss();
            } else if (!sources.equals("Select News Sources")) {
                presenter.getNewsWithSource(sources);
                progressNews.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onRefresh() {
        progressNews.setVisibility(View.VISIBLE);
        presenter.getNews(country);
        if (isCountry) {
            presenter.getNews(country);

        } else {
            presenter.getNewsWithSource(sources);
        }
    }
}
