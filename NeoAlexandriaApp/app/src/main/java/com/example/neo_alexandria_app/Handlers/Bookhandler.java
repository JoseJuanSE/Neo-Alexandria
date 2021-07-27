package com.example.neo_alexandria_app.Handlers;

import android.content.Context;
import android.util.Log;

import com.example.neo_alexandria_app.BuildConfig;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.Interfaces.OnBooksCompleted;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Bookhandler {


    public static final String TAG = "Bookhandler";
    OnBooksCompleted bookListener;

    public Bookhandler(OnBooksCompleted listener) {
        bookListener = listener;
    }

    public void getBooks(String query) {
        //BOOKS
        String urlBooks = "https://bookmeth1.p.rapidapi.com/";
        AsyncHttpClient clientBooks = new AsyncHttpClient();
        clientBooks.addHeader("x-rapidapi-key", BuildConfig.RAPID_APIKEY);
        clientBooks.addHeader("x-rapidapi-host", "bookmeth1.p.rapidapi.com");
        RequestParams params = new RequestParams();
        params.put("q", query);
        clientBooks.get(urlBooks, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    List<Book> books = Book.fromJsonArray(response);
                    Log.e(TAG, "success on books request");
                    bookListener.bookTaskCompleted(books);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e(TAG + " onFailure", res);
                List<Book> books = new ArrayList<>();
                bookListener.bookTaskCompleted(books);
            }
        });
    }

}
