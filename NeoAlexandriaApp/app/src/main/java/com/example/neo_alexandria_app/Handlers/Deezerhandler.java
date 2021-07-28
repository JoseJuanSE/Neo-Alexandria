package com.example.neo_alexandria_app.Handlers;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.example.neo_alexandria_app.Activities.SignupActivity;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.Interfaces.OnMusicCompleted;
import com.example.neo_alexandria_app.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;


public class Deezerhandler {

    public static final String TAG = "Deezerhandler";

    OnMusicCompleted musicListener;
    Context context;

    public Deezerhandler(Context context, OnMusicCompleted musicListener) {
        this.musicListener = musicListener;
        this.context = context;
    }

    public void getSongs(String title, View view) {


        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.hideConfirmButton();
        pDialog.setCustomView(view);
        pDialog.show();

        String url = "https://api.deezer.com/search";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", title);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = new JSONArray(response.getString("data"));
                    List<Song> songs = Song.fromJsonArray(jsonArray);
                    musicListener.musicTaskCompleted(songs, pDialog);
                } catch (JSONException | ParseException e) {
                    Log.e("onSuccess " + TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e(TAG + " onFailure", res);
                List<Song> songs = new ArrayList<>();
                musicListener.musicTaskCompleted(songs, pDialog);
            }
        });
    }

}
