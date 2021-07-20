package com.example.neo_alexandria_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neo_alexandria_app.R;

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


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}