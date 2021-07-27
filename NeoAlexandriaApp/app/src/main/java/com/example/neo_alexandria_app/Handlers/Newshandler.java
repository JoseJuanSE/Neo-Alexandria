package com.example.neo_alexandria_app.Handlers;

import android.util.Log;

import com.example.neo_alexandria_app.BuildConfig;
import com.example.neo_alexandria_app.Interfaces.OnNewsCompleted;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class Newshandler {

    OnNewsCompleted newsCompleted;

    public Newshandler(OnNewsCompleted onNewsCompleted) {
        newsCompleted = onNewsCompleted;
    }

    public void getNews(String query) {
        //News API
        NewsApiClient newsApiClient = new NewsApiClient(BuildConfig.NEWS_APIKEY);

        // /v2/everything
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        List<String> titles = new ArrayList<>();
                        for (int i = 0; i < response.getArticles().size(); i++) {
                            titles.add(response.getArticles().get(i).getTitle() + " News");
                        }
                        newsCompleted.newsTaskCompleted(titles);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
    }
}
