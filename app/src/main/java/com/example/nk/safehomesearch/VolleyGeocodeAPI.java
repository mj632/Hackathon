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

public class VolleyGeocodeAPI {

    private Context mContext;
    private static final String TAG = "VolleyGeocodeAPI";
    private String address;

    public VolleyGeocodeAPI(Context context) {
        mContext = context;
    }

    public void makeRequest(final int position, LatLng latLng) {


        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = AppConstants.GOOGLE_REVERSE_GEOCODE_URL + latLng.latitude + "," + latLng.longitude + "&key=AIzaSyAZc7jJydUQy6w4QLzzxP9VaaiJfac5ztE";
        Log.d(TAG, "makeRequest: URL:" + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        address = parseAddress(response);
                        ((MapsActivity) mContext).getAddress(position, address);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                ((MapsActivity) mContext).getAddress(position, "");
            }
        });


        queue.add(stringRequest);
    }

    private String parseAddress(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getJSONArray("results").getJSONObject(0).getString("formatted_address");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
