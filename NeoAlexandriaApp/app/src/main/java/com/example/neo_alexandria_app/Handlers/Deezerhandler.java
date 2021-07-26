package com.example.neo_alexandria_app.Handlers;

import android.app.DownloadManager;
import android.net.Uri;
import android.util.Log;

import com.example.neo_alexandria_app.Activities.SignupActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;


public class Deezerhandler {

    //TODO: TRY TO DO IT IN THIS WAY, FOR NOW THIS IS NOT WORKING

    static JSONArray jsonArray;
    static Timer timer;
    static AsyncHttpClient client;
    static RequestParams params;
    static String url;

    public static JSONArray getSongs(String title) {

        url = "https://api.deezer.com/search";
        client = new AsyncHttpClient();
        params = new RequestParams();
        params.put("q", title);

        WaitForAPIresponose();

        return jsonArray;
    }

    static void WaitForAPIresponose() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            jsonArray = new JSONArray(response.getString("data"));
                        } catch (JSONException e) {
                            Log.e("onSuccess Deezerhandler", e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("dsa", "AQUI NO");
                    }
                });
            }
        }, 2000);
    }

}
