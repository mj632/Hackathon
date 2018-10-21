package com.example.nk.safehomesearch;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private static int RESULT_LOAD_IMAGE = 1;
    private RecyclerView imgUploadRecyclerView;
    private ReviewAdapter mAdapter;
    private List<Uri> listOfImages = new ArrayList<>();
    private String userId = "";
    private EditText reviewTitle;
    private MongoDBHelper mongoDBHelper;
//    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        reviewTitle = findViewById(R.id.reviewTitle);
        imgUploadRecyclerView = (RecyclerView) findViewById(R.id.imgUploadRecyclerView);

        Intent intent = getIntent();
        if (intent.hasExtra("USER_ID")) {
            userId = intent.getStringExtra("USER_ID").toString();
//            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else {
            userId = "5bcb996a3e0e0b9d27d34e70";
        }

        mAdapter = new ReviewAdapter(listOfImages, this);
        imgUploadRecyclerView.setAdapter(mAdapter);
        imgUploadRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mongoDBHelper = new MongoDBHelper(this);
    }

    public void uploadNewImage(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                listOfImages.add(selectedImage);

                mAdapter.notifyDataSetChanged();
            } else {
            }
        } else {
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public void onSubmitReview(View v){
//        String review = reviewTitle.getText().toString();
        ArrayList<String> review = new ArrayList<>();
        review.add(reviewTitle.getText().toString());
        mongoDBHelper.insertMoreReviews(41.93594856,-87.5444032,review, userId);
        
    }

    public void goBackTOParent(){
        reviewTitle.setText("");
        Intent searchIntent = new Intent(ReviewActivity.this, SearchActivity.class);
//                    searchIntent .putExtra("USER_ID", userID);
        finish();

    }
}
