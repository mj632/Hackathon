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

import org.json.JSONObject;

public class VolleyShineAPI {

    private Context mContext;
    private static final String TAG = "VolleyShineAPI";

    public VolleyShineAPI(Context context) {
        mContext = context;
    }

    public void makeRequest(LatLng latLng) {


        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://apis.solarialabs.com/shine/v1/total-home-scores/reports?lat=" + latLng.latitude + "&lon=" + latLng.longitude + "&apikey=KRCvKeiZqh9GnR9npxNq4kysV7jiaeC0";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d(TAG, "onResponse: " + response);
                        Intent safeHomeDetails = new Intent(mContext, SafeHomeDetailsActivity.class);
                        SafeHomeDetail safeHomeDetail = parseHomeDeatils(response);
                        safeHomeDetails.putExtra(AppConstants.SAFE_HOME_INTENT, safeHomeDetail);
                        mContext.startActivity(safeHomeDetails);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });


        queue.add(stringRequest);
    }

    private SafeHomeDetail parseHomeDeatils(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject totalScoreJSONObject = jsonObject.getJSONObject(AppConstants.JSON_TOTALSCORE);
            SafeHomeDetail safeHomeDetail = new SafeHomeDetail();
            safeHomeDetail.setSafeScore(totalScoreJSONObject.getJSONObject(AppConstants.JSON_SAFE).getDouble("value"));
            safeHomeDetail.setQuietScore(totalScoreJSONObject.getJSONObject(AppConstants.JSON_QUIET).getDouble("value"));
            safeHomeDetail.setTrafficScore(totalScoreJSONObject.getJSONObject(AppConstants.JSON_TRAFFIC).getDouble("value"));
            safeHomeDetail.setErrandScore(totalScoreJSONObject.getJSONObject(AppConstants.JSON_ERRAND).getDouble("value"));
            safeHomeDetail.setEntertainmentScore(totalScoreJSONObject.getJSONObject(AppConstants.JSON_ENTERTAINTMENT).getDouble("value"));
            return safeHomeDetail;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
