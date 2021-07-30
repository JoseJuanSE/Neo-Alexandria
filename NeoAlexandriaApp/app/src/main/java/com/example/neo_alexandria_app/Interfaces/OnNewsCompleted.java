package com.example.neo_alexandria_app.Interfaces;

import com.example.neo_alexandria_app.DataModels.News;

import java.util.List;

public interface OnNewsCompleted {
    public void newsTaskCompleted(List<News> news);
}
