package com.example.neo_alexandria_app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NSFWhandler extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String url = "https://nsfw-image-classification1.p.rapidapi.com/img/nsfw";
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.addHeader("x-rapidapi-key", BuildConfig.RAPID_APIKEY);
//        client.addHeader("x-rapidapi-host", "nsfw-image-classification1.p.rapidapi.com");
//        RequestParams params = new RequestParams();
//        params.put("url", "https://www.inferdo.com/img/nsfw-1-raw.jpg");
//        client.get(url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.e("dsa","AQUI ANDAMOS");
//                Log.e("unique", response.toString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.e("dsa","AQUI NO");
//            }
//        });
    }
}
