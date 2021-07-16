package com.example.gettingdatafromapisexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

public class MainActivity extends AppCompatActivity {
    String title;
    String author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NewsApiClient newsApiClient = new NewsApiClient("ae88d8c2604b43e3a5f84ca3942c14c7");

// /v2/everything
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("trump")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        title = response.getArticles().get(0).getTitle();
                        author = response.getArticles().get(0).getAuthor();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
    }
}