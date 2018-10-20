package com.example.nk.safehomesearch;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private static int RESULT_LOAD_IMAGE = 1;
    private RecyclerView imgUploadRecyclerView;
    private ReviewAdapter mAdapter;
    private List<Uri> listOfImages = new ArrayList<>();
//    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        imgUploadRecyclerView = (RecyclerView) findViewById(R.id.imgUploadRecyclerView);

        Intent intent = getIntent();
        if (intent.hasExtra("USERNAME")) {
            String text = intent.getStringExtra("USERNAME").toString();
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }

        mAdapter = new ReviewAdapter(listOfImages, this);
        imgUploadRecyclerView.setAdapter(mAdapter);
        imgUploadRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
}
