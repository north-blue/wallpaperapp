package com.example.wallpaperapidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN=5000;
    Animation bottom;

    TextView imageTextView;
    TextView wallTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();


        setContentView(R.layout.activity_main);

        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom);

        imageTextView=findViewById(R.id.imageTextView);
        wallTextView=findViewById(R.id.wallTextView);

        wallTextView.setAnimation(bottom);
        imageTextView.setAnimation(bottom);
        


        new Handler().postDelayed(() -> {

            Intent intent =new Intent(MainActivity.this,Wallpapemain.class);
            startActivity(intent);
            finish();

        },SPLASH_SCREEN);



    }
}