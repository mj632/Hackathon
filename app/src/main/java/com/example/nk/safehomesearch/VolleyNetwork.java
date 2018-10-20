package com.example.nk.safehomesearch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

public class VolleyNetwork {

    private Context mContext;
    private static final String TAG = "VolleyNetwork";

    public VolleyNetwork(Context context) {
        mContext = context;
    }

    public void makeRequest(LatLng latLng) {


        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://apis.solarialabs.com/shine/v1/total-home-scores/reports?lat=" + latLng.latitude + "&lon=" + latLng.longitude + "&apikey=RPy6VypsIXX6cZUC3yS4MIandJMKKN5I";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d(TAG, "onResponse: " + response.substring(0, 500));
                        Intent safeHomeDetails = new Intent(mContext,SafeHomeDetails.class);
                        mContext.startActivity(safeHomeDetails);
                        //mTextView.setText("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });


        queue.add(stringRequest);
    }
}
