package com.example.neo_alexandria_app.DataModels;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Item {

    @IntDef({ItemType.SONG_TYPE, ItemType.BOOK_TYPE, ItemType.NEWS_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemType {
        int SONG_TYPE = 0;
        int BOOK_TYPE = 1;
        int NEWS_TYPE = 2;
    }
    @ItemType
    private int type;
    private float rating;
    private Object object;

    public Item(@ItemType int type, Object object, float rating) {
        this.type = type;
        this.object = object;
        this.rating = rating;
    }
    @ItemType
    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

    public float getRating() {
        return rating;
    }

    public void setRating (float rating) {
        this.rating = rating;
    }
}
