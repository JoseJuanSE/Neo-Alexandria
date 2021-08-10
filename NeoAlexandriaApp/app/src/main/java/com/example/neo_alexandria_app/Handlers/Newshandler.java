package com.example.neo_alexandria_app.Handlers;

import com.example.neo_alexandria_app.BuildConfig;
import com.example.neo_alexandria_app.DataModels.News;
import com.example.neo_alexandria_app.Interfaces.OnNewsCompleted;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
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
                        List<News> news = new ArrayList<>();
                        for (int i = 0; i < response.getArticles().size(); i++) {
                            News new1 = new News();
                            Article article = response.getArticles().get(i);

                            new1.setDate(StringsHandler.getRelativeTimeAgoFromString(article.getPublishedAt()));
                            new1.setDescription("");
                            new1.setAuthorName("");
                            new1.setTitle("");
                            new1.setImageLink("");
                            new1.setExternalLink("");
                            new1.setSourceName("");

                            if (article.getDescription() != null) {
                                new1.setDescription(article.getDescription());
                            }
                            if (article.getAuthor() != null) {
                                new1.setAuthorName(article.getAuthor());
                            }
                            if (article.getTitle() != null) {
                                new1.setTitle(article.getTitle());
                            }
                            if (article.getUrlToImage() != null) {
                                new1.setImageLink(article.getUrlToImage());
                            }
                            if (article.getUrl() != null) {
                                new1.setExternalLink(article.getUrl());
                            }
                            if (article.getSource().getName() != null) {
                                new1.setSourceName(article.getSource().getName());
                            }


                            //This have to be fill with parse
                            new1.setCommentCount((int) Math.max(3, 100 * Math.random()));
                            new1.setSaveCount((int) Math.max(3, 100 * Math.random()));
                            new1.setSaved(false);
                            new1.setRating(Math.max(1f, 5 * (float) Math.random()));
                            String aux = "";

                            for (int j = new1.getTitle().length() - 1; j >= 0; j--) {
                                aux += new1.getTitle().charAt(j);
                            }

                            new1.setId(StringsHandler.limited(aux, Math.min(8, aux.length())));

                            news.add(new1);
                        }
                        newsCompleted.newsTaskCompleted(news);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
    }
}
