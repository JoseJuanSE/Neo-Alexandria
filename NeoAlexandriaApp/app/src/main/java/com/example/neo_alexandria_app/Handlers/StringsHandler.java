package com.example.neo_alexandria_app.Handlers;

public class StringsHandler {
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
}
