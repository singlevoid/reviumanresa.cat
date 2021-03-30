package com.singlevoid.caterina.data.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;
import com.singlevoid.caterina.ui.main.MainActivity;

public class PhotographClusterManager extends ClusterManager<PhotographMarker>
        implements ClusterManager.OnClusterItemClickListener<PhotographMarker> {

    private final Context context;

    public PhotographClusterManager(Context context, GoogleMap map) {
        super(context, map);
        this.context = context;
        setRenderer(new PhotographClusterRenderer<>(context, map, this));
        setOnClusterItemClickListener(this);
    }

    @Override
    public boolean onClusterItemClick(PhotographMarker item) {
        MainActivity activity = (MainActivity) context;
        activity.openPhotographDetail(item.getPhotograph());
        return false;
    }

}