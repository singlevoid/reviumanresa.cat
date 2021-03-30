package com.singlevoid.caterina.ui.main.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.map.PhotographClusterManager;
import com.singlevoid.caterina.data.map.PhotographMarker;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.data.settings.AppTheme;
import com.singlevoid.caterina.utils.AppUtils;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    private static ClusterManager<PhotographMarker> clusterManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMapAsync();
    }


    private void getMapAsync() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    private void centerMap(GoogleMap map) {
        if(areLatLngArguments())        { centerMapToArguments(map); }
        else                            { centerMapToCityCenter(map); }
    }


    private void centerMapToArguments(GoogleMap map){
        LatLng center = new LatLng(getArguments().getFloat("Latitude"), getArguments().getFloat("Longitude"));
        CameraPosition position = new CameraPosition.Builder().target(center).zoom(18f).tilt(20f).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }


    private boolean areLatLngArguments(){
        return  getArguments() != null
                && getArguments().getFloat("Latitude") != 0.0
                && getArguments().getFloat("Longitude") != 0.0;

    }


    private void centerMapToCityCenter(GoogleMap map) {
        LatLng center = new LatLng(41.7247179, 1.8248544);
        CameraPosition position = new CameraPosition.Builder().target(center).zoom(15.5f).tilt(20f).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }


    private void loadMarkers(PhotographManager photographs) {
        for (Photograph photograph : photographs.getLocalized()) { addMarker(photograph); }
        clusterManager.cluster();
    }


    private void addMarker(Photograph photograph){
        clusterManager.addItem(new PhotographMarker(photograph.getLatitude(), photograph.getLongitude(), photograph));
    }


    @Override
    public void onMapReady(GoogleMap map) {
        configureMap(map);
        loadMyLocationIfEnabled(map);
        initClusterManager(map);
       initDataViewModel();

    }


    private void configureMap(GoogleMap map) {
        setMapStyle(map);
        centerMap(map);
    }


    private void initDataViewModel() {
        DataViewModel dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.getPhotographs(requireContext())
                .observe(getViewLifecycleOwner(), this::loadMarkers);
    }


    @SuppressLint("PotentialBehaviorOverride")
    private void initClusterManager(GoogleMap map){
        clusterManager = new PhotographClusterManager(this.requireContext(), map);
        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);
    }


    @SuppressLint("MissingPermission")
    private void loadMyLocationIfEnabled(GoogleMap map) {
        if (AppUtils.isLocationAllowed(requireContext())) {
            map.setMyLocationEnabled(true);
        }
    }

    public void setMapStyle(GoogleMap googleMap){
        AppTheme theme = new AppTheme();
        if (theme.isNightUsed(requireContext())) {
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));
        }

    }
}