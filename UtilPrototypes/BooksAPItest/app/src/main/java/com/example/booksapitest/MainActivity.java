package com.example.booksapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://bookmeth1.p.rapidapi.com/";
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-rapidapi-key", BuildConfig.RAPID_APIKEY);
        client.addHeader("x-rapidapi-host", "bookmeth1.p.rapidapi.com");
        RequestParams params = new RequestParams();
        params.put("q", "Harry Potter");
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("dsa","AQUI ANDAMOS");
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject object = response.getJSONObject(i);
                        Log.e("unique", "title: "+object.getJSONObject("_source").getString("title")+" object: "+object.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("dsa","AQUI NO");
            }
        });
    }
}