package com.example.wallpaperapidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import cn.bluemobi.dylan.photoview.library.PhotoView;

public class FullScreenWallpaper extends AppCompatActivity {

    String originalUrl ="";
    PhotoView photoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_wallpaper);

        getSupportActionBar().hide();


        Intent intent= getIntent();
        originalUrl = intent.getStringExtra("originalUrl");
        photoView = findViewById(R.id.photoView);

        Glide.with(this).load(originalUrl).into(photoView);

        //for the back
       //super.onBackPressed();


    }

    public void SetWallpaperEvent(View view) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Bitmap bitmap =((BitmapDrawable)photoView.getDrawable()).getBitmap();

        //allow the permission to set wallpaper


        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void DownloadWallpaperEvent(View view) {
        DownloadManager downloadManager =(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(originalUrl);
        DownloadManager.Request request =new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading Start", Toast.LENGTH_SHORT).show();


    }
}