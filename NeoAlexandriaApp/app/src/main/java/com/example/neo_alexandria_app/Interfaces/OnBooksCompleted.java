package com.example.neo_alexandria_app.Interfaces;

import com.example.neo_alexandria_app.DataModels.Book;

import java.util.List;

public interface OnBooksCompleted {
    public void bookTaskCompleted(List<Book> books);
}
