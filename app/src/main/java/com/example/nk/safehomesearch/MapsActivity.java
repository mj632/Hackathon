package com.example.nk.safehomesearch;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private List<LatLng> mLocationsList = new ArrayList<>();
    private VolleyNetwork volleyNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        volleyNetwork = new VolleyNetwork(this);

        makeLatLngList();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_style));
        mMap = googleMap;
        LatLng position = new LatLng(mLocationsList.get(0).latitude, mLocationsList.get(0).longitude);
        for (int i = 0; i < mLocationsList.size(); i++) {
            position = mLocationsList.get(i);
            googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title("Position")
                    .snippet("Latitude:" + mLocationsList.get(i).latitude + ",Longitude:" + mLocationsList.get(i).longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_house_marker)));
        }
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                position, 13);
        mMap.animateCamera(location);
        mMap.setOnInfoWindowClickListener(this);

    }

    private void makeLatLngList() {
        LatLng latLng = new LatLng(41.8418742, -87.6377098);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.8370782, -87.626466);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.8382932, -87.6253502);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.844764, -87.634616);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.846162, -87.627614);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.846371, -87.608306);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.831338, -87.620211);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.84519, -87.621453);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.844038, -87.618263);
        mLocationsList.add(latLng);
        latLng = new LatLng(41.843678, -87.613961);
        mLocationsList.add(latLng);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        volleyNetwork.makeRequest(marker.getPosition());

    }
}
