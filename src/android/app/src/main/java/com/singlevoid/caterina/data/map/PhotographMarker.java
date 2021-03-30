package com.singlevoid.caterina.data.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.singlevoid.caterina.data.photograph.Photograph;

public class PhotographMarker implements ClusterItem {

    private LatLng position;
    private String title;
    private Photograph photograph;

    public PhotographMarker(double lat, double lng){
        this.position = new LatLng(lat, lng);
    }

    public PhotographMarker(double lat, double lng, Photograph photograph) {
        this.position = new LatLng(lat, lng);
        this.photograph = photograph;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() { return null; }

    @Nullable
    public Photograph getPhotograph() {
        return photograph;
    }
}
