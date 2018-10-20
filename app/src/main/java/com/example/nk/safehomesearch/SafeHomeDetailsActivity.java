package com.example.nk.safehomesearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class SafeHomeDetailsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset = {"", "", "", "", ""};
    private TextView mSafetyReviewTV;
    private TextView mPositiveFactors;
    private TextView mNegativeFactors;
    private TextView mUserReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safehome_details);

        Bundle data = getIntent().getExtras();
        SafeHomeDetail safeHomeDetail = (SafeHomeDetail) data.getParcelable(AppConstants.SAFE_HOME_INTENT);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        mSafetyReviewTV = (TextView) findViewById(R.id.safetyReviewTextView);
        mPositiveFactors = (TextView) findViewById(R.id.positiveDescription);
        mNegativeFactors = (TextView) findViewById(R.id.negativeDescription);
        mUserReviews = (TextView) findViewById(R.id.userReviewDescription);

        if (safeHomeDetail.getSafeScore() >= 70) {
            mSafetyReviewTV.setText("This address is a safe place to stay");
            mSafetyReviewTV.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else if (safeHomeDetail.getSafeScore() < 70 || safeHomeDetail.getSafeScore() >= 60) {
            mSafetyReviewTV.setText("This address is a moderately safe place to stay");
            mSafetyReviewTV.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            mSafetyReviewTV.setText("This address is a not safe place to stay");
            mSafetyReviewTV.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        if (safeHomeDetail.getQuietScore() >= 75) {
            mPositiveFactors.setText(mPositiveFactors.getText() + "\nIs a Quiet neighbourhood");
        } else {
            mNegativeFactors.setText(mNegativeFactors.getText() + "\nIs not a very Quiet neighbourhood");
        }

        if (safeHomeDetail.getTrafficScore() >= 75) {
            mPositiveFactors.setText(mPositiveFactors.getText() + "\nLess Traffic");
        } else {
            mNegativeFactors.setText(mNegativeFactors.getText() + "\nMore Traffic");
        }

        if (safeHomeDetail.getErrandScore() >= 75) {
            mPositiveFactors.setText(mPositiveFactors.getText() + "\nShopping Areas at close proximity");
        } else {
            mNegativeFactors.setText(mNegativeFactors.getText() + "\nShopping Areas not at a close proximity");
        }

        if (safeHomeDetail.getEntertainmentScore() >= 75) {
            mPositiveFactors.setText(mPositiveFactors.getText() + "\nHas quiet a few entertainment places close by");
        } else {
            mNegativeFactors.setText(mNegativeFactors.getText() + "\nDoes not have entertainment places close by");
        }
    }
}
