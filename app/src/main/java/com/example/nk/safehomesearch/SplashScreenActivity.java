package com.example.nk.safehomesearch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mNextImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mNextImageView = (ImageView)findViewById(R.id.splash_next_imageview);
        mNextImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent aboutAppIntent = new Intent(SplashScreenActivity.this, AboutAppActivity.class);
        SplashScreenActivity.this.startActivity(aboutAppIntent);
    }
}
