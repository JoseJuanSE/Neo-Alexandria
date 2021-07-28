package com.example.neo_alexandria_app.DataModels;

public class Item {
    private int type;
    public float rating;
    private Object object;

    public Item(int type, Object object, float rating) {
        this.type = type;
        this.object = object;
        this.rating = rating;
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}
