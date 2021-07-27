package com.example.neo_alexandria_app.Handlers;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.neo_alexandria_app.BuildConfig;
import com.example.neo_alexandria_app.OnNSFWCompleted;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class NSFWhandler {

    OnNSFWCompleted NSFWlistener;
    Context context;

    public NSFWhandler(Context context, OnNSFWCompleted listener) {
        this.NSFWlistener = listener;
        this.context = context;
    }

    public void get(String image) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper();
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        AndroidNetworking.post("https://nsfw-image-classification1.p.rapidapi.com/img/nsfw")
                .addHeaders("content-type", "application/json")
                .addHeaders("x-rapidapi-key", BuildConfig.RAPID_APIKEY)
                .addHeaders("x-rapidapi-host", "nsfw-image-classification1.p.rapidapi.com")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            double probability = Double.parseDouble(response.get("NSFW_Prob").toString());
                            NSFWlistener.taskCompleted(probability, pDialog);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("unique", "Error: " + e);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("unique", anError.getErrorBody());
                    }
                });
    }
}
