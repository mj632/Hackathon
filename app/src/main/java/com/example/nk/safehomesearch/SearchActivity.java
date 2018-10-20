package com.example.nk.safehomesearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private static final int MAPS_REQUEST_CODE = 1;
    private static final int LOGIN_REQUEST_CODE = 2;
    private String username = "admin";
    private EditText addressBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        addressBox = findViewById(R.id.addressBox);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                username = data.getStringExtra("USER_NAME");
//                userText.setText(text);
            } else {
            }
        } else {
        }
    }




    public void searchInArea(View v) {
        Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
        intent.putExtra("AREA_ZIP_CODE", addressBox.getText());
        startActivity(intent);
    }

    public void goToLogin(View v){
        Intent loginIntent = new Intent(SearchActivity.this, LoginActivity.class);
         startActivityForResult(loginIntent, LOGIN_REQUEST_CODE);
    }

    public void goToReviewPage(View v){
        Intent reviewIntent = new Intent(SearchActivity.this, ReviewActivity.class);
        reviewIntent.putExtra("USERNAME", username);
        startActivity(reviewIntent);
    }

}
