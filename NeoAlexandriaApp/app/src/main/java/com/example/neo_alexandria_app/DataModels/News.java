package com.example.neo_alexandria_app.DataModels;

import java.io.Serializable;

public class News extends Resource implements Serializable {

    public static final String TAG = "News";

    String sourceName;
    String description;
    String date;


    public News() {

    }


    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

}
