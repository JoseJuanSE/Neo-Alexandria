package com.example.neo_alexandria_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neo_alexandria_app.R;
import com.parse.ParseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //set animations
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.upanim);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.downanim);

        TextView tvNeo = findViewById(R.id.tvlogoS);
        TextView tvSearch = findViewById(R.id.tvSearch);
        ImageView ivlogos = findViewById(R.id.ivlogoS);

        tvNeo.setAnimation(animation1);
        ivlogos.setAnimation(animation1);
        tvSearch.setAnimation(animation2);

        ParseUser.logOut();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                // If we are already log in, we have to skip the log in.
                if (ParseUser.getCurrentUser() == null) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(ivlogos, "logoImageTrans");
                pairs[1] = new Pair<View, String>(tvNeo, "textTrans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                startActivity(intent, options.toBundle());
                finish();
            }
        }, 4000);
    }
}