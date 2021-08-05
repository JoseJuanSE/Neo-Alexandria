package com.example.neo_alexandria_app.Handlers;

import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class StringsHandler {

    public static final String TAG = "StringsHandler";
    public static final int SECOND_MILLIS = 1000;
    public static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    //Here we reuse this code in for some activities.
    public static String limited(String title, int lit) {
        String ans = "";

        for (int i = 0; i < Math.min(lit, title.length()); i++) {
            ans += title.charAt(i);
        }
        if (title.length() > lit) {
            ans += "...";
        }
        return ans;
    }

    public static String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {

        String twitterFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {

            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 60 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + "m";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + "h";
            } else {
                return diff / DAY_MILLIS + "d";
            }

        } catch (ParseException e) {

            Log.i(TAG, "getRelativeTimeAgo failed");
            e.printStackTrace();

        }

        return "";
    }
    public static boolean validateField(TextInputLayout textInputLayout, EditText editText) {
        if(editText.getText().toString().isEmpty()) {
            textInputLayout.setError("Field can't be empty");
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }
}
