package com.example.nk.safehomesearch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn,cancelBtn;
    EditText useraName,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        useraName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        cancelBtn = (Button) findViewById(R.id.cancelBtn);
    }
    public void checkCreds(View v) {
        if (useraName.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
            Intent backToParentIntent = new Intent();
            backToParentIntent.putExtra("USER_NAME", useraName.getText().toString());
            setResult(RESULT_OK, backToParentIntent);
            super.finish();

        } else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        // Pressing the back arrow closes the current activity, returning us to the original activity
        Intent backToParentIntent = new Intent();
        backToParentIntent.putExtra("USER_NAME", useraName.getText().toString());
        setResult(RESULT_OK, backToParentIntent);
        super.onBackPressed();
    }

    public void onClickCancel(View v) {
        finish();
    }
}
