package com.example.nk.safehomesearch;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private List<LatLng> mLocationsList = new ArrayList<>();
    private VolleyShineAPI volleyNetwork;
    private ClusterManager<MarkerItem> mClusterManager;
    private static final String TAG = "MapsActivity";
    private MarkerItem clickedMarkerItem;
    private List<MarkerItem> mMarkerItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        volleyNetwork = new VolleyShineAPI(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpClusterer();
    }


    private void setUpClusterer() {

        LoadMapMarkers loadMapMarkers = new LoadMapMarkers(this);
        loadMapMarkers.execute();
    }

    public void getMapMarkers(List<MarkerItem> mapItemList) {
        mMarkerItemList = mapItemList;
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
        for (int i = 0; i < mapItemList.size(); i++) {
            callReverseGeocode(i, mapItemList.get(i).getPosition());
        }
    }

    public void getAddress(int position, String address) {
        Log.d(TAG, "getAddress: Updating map");
        MarkerItem markerItem = mMarkerItemList.get(position);
        markerItem.setTitle("Address:");
        markerItem.setSnippet(address);
        mClusterManager.addItem(markerItem);
        mMap.moveCamera( CameraUpdateFactory.zoomTo(6.5f));
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

    private void callReverseGeocode(int position, LatLng latLng) {
        VolleyGeocodeAPI volleyGeocodeAPI = new VolleyGeocodeAPI(this);
        volleyGeocodeAPI.makeRequest(position, latLng);
    }

}
