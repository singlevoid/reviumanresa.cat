////////////////////////////////////////////////////////////////////////////////////////////////////
//                                      LICENSE                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                //
// Copyright [2020] [Joan Albert Espinosa Muns]                                                   //
//                                                                                                //
// Licensed under the Apache License, Version 2.0 (the "License")                                 //
// you may not use this file except in compliance with the License.                               //
// You may obtain a copy of the License at                                                        //
//                                                                                                //
// http://www.apache.org/licenses/LICENSE-2.0                                                     //
//                                                                                                //
// Unless required by applicable law or agreed to in writing, software                            //
// distributed under the License is distributed on an "AS IS" BASIS,                              //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                       //
// See the License for the specific language governing permissions and                            //
// limitations under the License.                                                                 //
//                                                                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

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

import org.jetbrains.annotations.NotNull;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private DataViewModel dataViewModel;
    private View root;
    private static ClusterManager<PhotographMarker> clusterManager;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_map, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void init() {
        initViewModel();
        getMapAsync();
        setListeners();
    }

    private void initViewModel() {
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    private void getMapAsync() {
        if (getMapFragment() != null) {
            getMapFragment().getMapAsync(this);
        }
    }


    private void setListeners() {
        getBackground().setOnClickListener(this::backPressed);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private SupportMapFragment getMapFragment() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
    }


    private View getBackground(){
        return root.findViewById(R.id.fragment_collection_filter_container);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void centerMap(GoogleMap map) {
        if(areLatLngArguments())        { centerMapToArguments(map); }
        else                            { centerMapToCityCenter(map); }
    }


    private void centerMapToArguments(@NotNull GoogleMap map){
        LatLng center = new LatLng(getArguments().getFloat("Latitude"), getArguments().getFloat("Longitude"));
        CameraPosition position = new CameraPosition.Builder().target(center).zoom(18f).tilt(20f).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }


    private boolean areLatLngArguments(){
        return  getArguments() != null
                && getArguments().getFloat("Latitude") != 0.0
                && getArguments().getFloat("Longitude") != 0.0;
    }


    private void centerMapToCityCenter(@NotNull GoogleMap map) {
        LatLng center = new LatLng(41.7247179, 1.8248544);
        CameraPosition position = new CameraPosition.Builder().target(center).zoom(15.5f).tilt(20f).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }


    private void loadMarkers(PhotographManager photographs) {
        dataViewModel.getFilters().observe(getViewLifecycleOwner(), filterManager -> {
            clusterManager.clearItems();
            for (Photograph photograph : filterManager.filter(photographs.getAll())) {
                if (photograph.isLocalized()){
                    addMarker(photograph);
                }
            }
            clusterManager.cluster();
        });
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
        dataViewModel.getPhotographs(requireContext()).observe(getViewLifecycleOwner(), this::loadMarkers);
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


    private void backPressed(View view) {
        getBackground().setVisibility(View.GONE);
    }
}