package com.example.neo_alexandria_app.Handlers;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.neo_alexandria_app.BuildConfig;
import com.parse.ParseFile;

import org.json.JSONException;
import org.json.JSONObject;


public class NSFWhandler {

    private static double probability;

    public static void get(String image){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url",image);
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
                        try {
                            probability = Double.parseDouble(response.get("NSFW_Prob").toString());
                            Log.e("unique", "NSFW pro: "+Double.toString(probability));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("unique", "Error: "+e);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("unique", anError.getErrorBody());
                    }
                });
    }

    public static double getProbability() {
        return probability;
    }
}
