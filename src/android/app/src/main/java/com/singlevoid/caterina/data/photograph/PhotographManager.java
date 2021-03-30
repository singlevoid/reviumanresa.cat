package com.singlevoid.caterina.data.photograph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.singlevoid.caterina.utils.AppUtils;

import java.util.ArrayList;

public class PhotographManager {

    public interface LocalizationListener {
        void onLocalized();
    }


    private final ArrayList<Photograph> photographs;
    private LocalizationListener locationListener;


    public ArrayList<Photograph> getAll() { return photographs; }


    public PhotographManager(){ photographs = new ArrayList<>(); }


    public void addLocationListener(LocalizationListener listener){
        this.locationListener = listener;
    }


    public void addPhotograph(Photograph photograph){ photographs.add(photograph); }


    public void sortByDistanceTo(){
        photographs.sort((photograph, other) -> (int) Math.round(photograph.getDistance() - other.getDistance()));
    }

    public void calculateDistanceTo(Location point){
        for(Photograph photograph: photographs){
            if( photograph.isLocalized()){
                photograph.setDistanceTo(point);
            }
        }
    }

    public ArrayList<Photograph> getLocalized(){
        ArrayList<Photograph> localized = new ArrayList<>();
        for (Photograph photograph: photographs){
            if(photograph.isLocalized()){ localized.add(photograph);}
        }
        return localized;
    }

    @SuppressLint("MissingPermission")
    public void localizePhotographs(Context context){
        if(AppUtils.isLocationAllowed(context)) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this::lastLocationListener);
        }
    }

    private void lastLocationListener(Location location) {
        if(location != null){
            calculateDistanceTo(location);
            locationListener.onLocalized();
        }
    }

    public ArrayList<Photograph> getNearPhotographs(Integer radius){
        ArrayList<Photograph> near = new ArrayList<>();
        for (Photograph photograph: photographs){
            if(photograph.isLocalized()){
                if(photograph.getDistance() <= radius) {
                    near.add(photograph);
                }
            }
        }
        return near;
    }

    public PhotographManager parseQuery(QuerySnapshot query){
        for (QueryDocumentSnapshot document : query){
            Photograph photograph = document.toObject(Photograph.class);
            photograph.setId(document.getId());
            addPhotograph(photograph);
        }
        return this;
    }
}
