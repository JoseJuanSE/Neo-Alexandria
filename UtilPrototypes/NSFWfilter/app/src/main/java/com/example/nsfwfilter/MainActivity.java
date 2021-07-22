package com.example.nsfwfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"url\": \"https://www.inferdo.com/img/nsfw-1-raw.jpg\" }");
//
//        Request request = new Request.Builder()
//            .url("https://nsfw-image-classification1.p.rapidapi.com/img/nsfw")
//            .post(body)
//            .addHeader("content-type", "application/json")
//            .addHeader("x-rapidapi-key", BuildConfig.RAPID_APIKEY)
//            .addHeader("x-rapidapi-host", "nsfw-image-classification1.p.rapidapi.com")
//            .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url","https://www.inferdo.com/img/nsfw-1-raw.jpg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://nsfw-image-classification1.p.rapidapi.com/img/nsfw")
                .addHeaders("content-type", "application/json")
                .addHeaders("x-rapidapi-key", BuildConfig.RAPID_APIKEY)
                .addHeaders("x-rapidapi-host", "nsfw-image-classification1.p.rapidapi.com")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("unique", response.toString());
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("unique", anError.getErrorBody());
                    }
                });

    }
}