package com.example.neo_alexandria_app;

import android.app.Application;

import com.example.neo_alexandria_app.DataModels.User;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {


        // Register your parse models


        super.onCreate();
        // set applicationId, and server server based on the values in the back4app setting.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with te Configuration Builder given this synth
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.PASRSE_ID)
                .clientKey(BuildConfig.PARSE_APIKEY)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
