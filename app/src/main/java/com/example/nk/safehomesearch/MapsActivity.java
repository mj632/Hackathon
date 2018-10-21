package com.example.nk.safehomesearch;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private List<LatLng> mLocationsList = new ArrayList<>();
    private VolleyNetwork volleyNetwork;
    private ClusterManager<MarkerItem> mClusterManager;
    private static final String TAG = "MapsActivity";
    private MarkerItem clickedMarkerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        volleyNetwork = new VolleyNetwork(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_style));*/
        mMap = googleMap;
        setUpClusterer();
        /*LatLng position = new LatLng(mLocationsList.get(0).latitude, mLocationsList.get(0).longitude);
        for (int i = 0; i < mLocationsList.size(); i++) {
            position = mLocationsList.get(i);
            googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title("Position")
                    .snippet("Latitude:" + mLocationsList.get(i).latitude + ",Longitude:" + mLocationsList.get(i).longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)));
        }
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                position, 13);
        mMap.animateCamera(location);
        mMap.setOnInfoWindowClickListener(this);*/

    }


    private void setUpClusterer() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.837617, -84.862918), 6));

        mClusterManager = new ClusterManager<MarkerItem>(this, mMap);
        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, mMap, mClusterManager);

        mClusterManager.setRenderer(renderer);
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                new MyCustomAdapterForItems());
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        mClusterManager
                .setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItem>() {
                    @Override
                    public boolean onClusterItemClick(MarkerItem item) {
                        clickedMarkerItem = item;
                        return false;
                    }
                });

        mMap.setOnInfoWindowClickListener(this);

        LoadMapMarkers loadMapMarkers = new LoadMapMarkers(this);
        loadMapMarkers.execute();
    }

    public void getMapMarkers(List<MarkerItem> mapItemList) {
        for (int i = 0; i < mapItemList.size(); i++) {
            mClusterManager.addItem(mapItemList.get(i));
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "onClusterItemInfoWindowClick: ");
        volleyNetwork.makeRequest(marker.getPosition());
    }

    public class MyCustomAdapterForItems implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyCustomAdapterForItems() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.info_window, null);

        }

        @Override
        public View getInfoWindow(Marker marker) {

            TextView tvTitle = ((TextView) myContentsView
                    .findViewById(R.id.txtTitle));
            TextView tvSnippet = ((TextView) myContentsView
                    .findViewById(R.id.txtSnippet));

            tvTitle.setText(clickedMarkerItem.getTitle());
            tvSnippet.setText(clickedMarkerItem.getSnippet());
            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
