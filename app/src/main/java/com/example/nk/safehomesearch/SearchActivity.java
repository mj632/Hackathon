package com.example.nk.safehomesearch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.core.auth.providers.facebook.FacebookCredential;

public class SearchActivity extends AppCompatActivity {

    private static final int MAPS_REQUEST_CODE = 1;
    private static final int LOGIN_REQUEST_CODE = 2;
//    private static final int LOGIN_REQUEST_CODE = 2;
    private String username = "admin";
    private EditText addressBox;
    public String userID = "";
    CallbackManager callbackManager;
    //static public StitchAppClient client;
    private static final String TAG = "SearchActivity";
    MongoDBHelper mongodbHelper;
    LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        addressBox = findViewById(R.id.addressBox);
        loginButton = (LoginButton) findViewById(R.id.feedbackNavigationBtn2);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mongodbHelper = new MongoDBHelper();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
//                    username = data.getStringExtra("USER_NAME");
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);

            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//                userText.setText(text);
        }
    }

    public void searchInArea(View v) {
        Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
        intent.putExtra("AREA_ZIP_CODE", addressBox.getText());
        startActivity(intent);
    }



    public void goToReviewPage(View v){
        Intent reviewIntent = new Intent(SearchActivity.this, ReviewActivity.class);
        reviewIntent.putExtra("USER_ID", userID);
        startActivity(reviewIntent);
    }
//============================================================================

    public void goToLogin(View v){
        /*Intent loginIntent = new Intent(SearchActivity.this, LoginActivity.class);
         startActivityForResult(loginIntent, LOGIN_REQUEST_CODE);*/
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    final FacebookCredential fbCre = new FacebookCredential(AccessToken.getCurrentAccessToken().getToken());
                    mongodbHelper.client.getAuth().loginWithCredential(fbCre).addOnCompleteListener(new OnCompleteListener<StitchUser>() {
                        @Override
                        public void onComplete(@NonNull Task<StitchUser> task) {
                            Log.d(TAG,task.isSuccessful()+"");
                            if(task.isSuccessful()){
                                userID = mongodbHelper.client.getAuth().getUser().getId();
                            }else{
                                Log.e(TAG,"error");
                            }
                        }
                    });
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
    }
}
