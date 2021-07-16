package com.example.songsapiexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://api.deezer.com/search";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", "eminem");
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                JSONArray array = null;
                try {
                    array = new JSONArray(response.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i=0;i<array.length();i++){
                    JSONObject object = null;
                    try {
                        object = array.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String title = null;
                    try {
                        title = object.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String duration = null;
                    try {
                        duration = object.getString("duration");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("this", "title: "+title+" duration: "+duration);
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