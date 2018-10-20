package com.example.nk.safehomesearch;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class AboutAppActivity extends Activity implements View.OnClickListener {

    private ImageView mAboutNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        mAboutNext = (ImageView) findViewById(R.id.about_app_next);
        mAboutNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent aboutAppIntent = new Intent(this, SearchActivity.class);
        this.startActivity(aboutAppIntent);
    }
}
