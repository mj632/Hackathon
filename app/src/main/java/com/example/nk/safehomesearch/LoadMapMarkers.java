package com.example.nk.safehomesearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadMapMarkers extends AsyncTask<String, Void, List<MarkerItem>> {
    private static final String TAG = "LoadMapMarkers";
    private List<MarkerItem> mMapItemList = new ArrayList<>();
    private Context mContext;

    LoadMapMarkers(Context context) {
        mContext = context;
    }

    @Override
    protected List<MarkerItem> doInBackground(String... params) {
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            double lat;
            double lng;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                lat = jsonObject.getDouble("lat");
                lng = jsonObject.getDouble("lon");
                double offset = i / 60d;
                MarkerItem offsetItem = new MarkerItem();
                offsetItem.setLatLng(new LatLng(lat, lng));
                lat = lat + offset;
                lng = lng + offset;
                offsetItem.setType(i % 3);
                mMapItemList.add(offsetItem);
            }
            Log.d(TAG, "loadMapMarkersFromJSON: Finished parsing JSON!");
        } catch (Exception e) {

        }
        return mMapItemList;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("map_markers_json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onPostExecute(List<MarkerItem> mapItemList) {
        ((MapsActivity) mContext).getMapMarkers(mapItemList);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}