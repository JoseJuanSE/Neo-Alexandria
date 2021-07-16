package com.example.neo_alexandria_app.DataModels;

import java.io.File;

public class Post {
    private String id;
    private User author;
    private String timeStamp;
    private String description;
    private File picture;
    private int likesCount;
    private int commentCount;
    private int saveCount;
    private boolean isLiked;
    private boolean isSaved;

    public Post () {

    }

}
