package com.example.neo_alexandria_app.DataModels;

public class Comment {
    private String userName;
    private String date;
    private String Content;
    private String ivProfileURL;
    private boolean Liked;

    public Comment() {
        this.Liked = false;
    }
    public boolean isLiked(){
        return this.Liked;
    }
    public void setLiked(){
        this.Liked = !this.Liked;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return Content;
    }

    public String getIvProfileURL() {
        return ivProfileURL;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setIvProfileURL(String ivProfileURL) {
        this.ivProfileURL = ivProfileURL;
    }

    public void setLiked(boolean liked) {
        Liked = liked;
    }
}
