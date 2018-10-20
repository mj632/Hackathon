package com.example.nk.safehomesearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private static final int MAPS_REQUEST_CODE = 1;
    private EditText addressBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        addressBox = findViewById(R.id.addressBox);
    }


    public void searchInArea(View v) {
        Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
        intent.putExtra("AREA_ZIP_CODE", addressBox.getText());
        startActivity(intent);
    }

}
