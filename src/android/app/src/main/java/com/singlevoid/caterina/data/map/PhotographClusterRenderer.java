package com.singlevoid.caterina.data.map;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.singlevoid.caterina.R;

import org.jetbrains.annotations.NotNull;


public class PhotographClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {

    private final Context context;

    public PhotographClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<T> cluster) {
        return cluster.getSize() > 5;
    }

    @Override
    protected void onClusterItemRendered(@NotNull T clusterItem, @NonNull Marker marker){
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        super.onClusterItemRendered(clusterItem, marker);
    }

    @Override
    protected int getColor(int clusterSize) {
        return this.context.getColor(R.color.primary);
    }
}