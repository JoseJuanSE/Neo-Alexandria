package com.example.neo_alexandria_app.DataModels;

public class Resource {
    String id;
    String title;
    String authorName;
    String imageLink;
    String externalLink;
    int commentCount;
    int saveCount;
    boolean isSaved;
    float rating;

    public Resource() {

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getSaveCount() {
        return saveCount;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public float getRating() {
        return rating;
    }
}
