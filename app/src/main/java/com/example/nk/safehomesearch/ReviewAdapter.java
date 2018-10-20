package com.example.nk.safehomesearch;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    List<Uri> listOfImages = new ArrayList<>();
    private ReviewActivity reviewActivity;

    public ReviewAdapter(List<Uri> listOfImages, ReviewActivity reviewActivity) {
        this.listOfImages = listOfImages;
        this.reviewActivity = reviewActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ConstraintLayout mConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView3);
            mConstraintLayout = (ConstraintLayout) itemView;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_image_row, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(view);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(listOfImages.size()==0){
            viewHolder.imageView.setImageResource(R.drawable.add_image_icon);
        } else{
            viewHolder.imageView.setImageURI(listOfImages.get(position));
        }

        viewHolder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewActivity.uploadNewImage();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfImages.size();
    }


}
