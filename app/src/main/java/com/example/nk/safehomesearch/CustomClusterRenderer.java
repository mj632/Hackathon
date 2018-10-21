package com.example.nk.safehomesearch;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

class CustomClusterRenderer extends DefaultClusterRenderer<MarkerItem> {

    private final Context mContext;

    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<MarkerItem> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(MarkerItem item,
                                               MarkerOptions markerOptions) {
        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.green_logo);
        if (item.getType() == 0) {
            markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.green_logo);
        }else if(item.getType() ==1){
            markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.yellow_logo);
        }else{
            markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.red_logo);
        }

        markerOptions.icon(markerDescriptor).snippet(item.getSnippet());
    }
}