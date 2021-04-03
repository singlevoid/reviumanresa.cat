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

package com.singlevoid.caterina.ui.filters.location;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterLocation;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.ui.filters.FilterModeView;
import com.singlevoid.caterina.ui.main.MainActivity;
import com.singlevoid.caterina.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterLocationFragment extends Fragment implements TextWatcher, PhotographManager.LocalizationListener {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View root;
    private DataViewModel data;
    private FilterManager filters;
    private PhotographManager photographs;
    private final ArrayList<FilterModeView> filterModes = new ArrayList<>();


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.filter_location, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    @Override
    public void onLocalized() {
        data.updatePhotographs(photographs);
        data.updateFilters(filters);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        initViewModel();
        initModeViews();
        setListeners();
    }


    private void initViewModel(){
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    private void initModeViews(){
        getModeAllView().configureFilterMode(FilterLocation.MATCH_ALL, R.string.filter_all);
        getModeGeolocationView().configureFilterMode(FilterLocation.MATCH_GEOLOCATED, R.string.filter_only_geolocalized);
        getModeNonGeolocationView().configureFilterMode(FilterLocation.MATCH_NO_GEOLOCATED, R.string.filter_only_non_localized);

        filterModes.add(getModeAllView());
        filterModes.add(getModeGeolocationView());
        filterModes.add(getModeNonGeolocationView());
    }


    private void setListeners(){
        data.getFilters().observe(getViewLifecycleOwner(), this::updateFilters);
        data.getPhotographs(requireContext()).observe(getViewLifecycleOwner(), this::updatePhotographs);
        getBackButtonView().setOnClickListener(this::navigateToFilters);
        getResetButtonView().setOnClickListener(this::resetFilters);
        getDistanceView().addTextChangedListener(this);

        for (FilterModeView filter: filterModes){
            filter.setOnClickListener(this::filterModeClicked);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @NotNull
    private MainActivity getMainActivity() {
        return ( (MainActivity) requireActivity() );
    }


    private TextView getResetButtonView() {
        return root.findViewById(R.id.filter_location_reset);
    }


    private ImageView getBackButtonView() {
        return root.findViewById(R.id.filter_location_back);
    }


    private FilterModeView getModeAllView() {
        return root.findViewById(R.id.filter_location_mode_all);
    }


    private FilterModeView getModeGeolocationView() {
        return root.findViewById(R.id.filter_location_mode_geolocation);
    }


    private FilterModeView getModeNonGeolocationView() {
        return root.findViewById(R.id.filter_location_mode_non_geolocation);
    }


    private View getOptionsViews() {
        return root.findViewById(R.id.filter_location_options);
    }


    private EditText getDistanceView() {
        return root.findViewById(R.id.filter_location_distance);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          METHODS                                           //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void updatePhotographs(PhotographManager photographManager) {
        photographs = photographManager;
    }


    private void filterModeClicked(View view){
        filters.getLocationFilter().setFilterMode(((FilterModeView) view).getFilterMode());
        data.updateFilters(filters);
    }


    private void resetFilters(View view) {
        filters.getLocationFilter().setToDefault();
        data.updateFilters(filters);
    }


    private void navigateToFilters(View view) {
        getMainActivity().filterNavigateTo(root, R.id.navigation_filters);
    }


    private void updateFilters(FilterManager filters) {
        this.filters = filters;
        updateVisibilityReset();
        updateFilterModeViews();
    }


    private boolean isLocationPermissionAllowed(){
        return AppUtils.isLocationAllowed(requireContext());
    }


    private void updateVisibilityReset(){
        if(filters.getLocationFilter().isDefault()) { getResetButtonView().setVisibility(View.INVISIBLE); }
        else                                        { getResetButtonView().setVisibility(View.VISIBLE); }
    }


    private void updateFilterModeViews(){
        updateFiltersModeViewStatus();
        updateOptionsViewStatus();
    }


    private void updateOptionsViewStatus(){


        if ( getModeGeolocationView().isActive() ){
            getOptionsViews().setVisibility(View.VISIBLE);
            if ( isLocationPermissionAllowed() ) {
                getDistanceView().setVisibility(View.VISIBLE);
            }
            else {
                getDistanceView().setVisibility(View.GONE);
            }
        }
        else{
            getOptionsViews().setVisibility(View.GONE);
        }


    }

    private void updateFiltersModeViewStatus(){
        for(FilterModeView filter: filterModes) {
            if(filter.getFilterMode() == filters.getLocationFilter().getFilterMode()){
                filter.setActive();
            }
            else{
                filter.setInactive();
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   TEXT WATCHER                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().equals("")) {
            filters.getLocationFilter().setDistanceRadius(null);
        }
        else if( 0<= Integer.parseInt(s.toString()) &&  Integer.parseInt(s.toString()) <= 25000){
            filters.getLocationFilter().setDistanceRadius(Integer.parseInt(s.toString()));
        }

        photographs.addLocationListener(this);
        photographs.localizePhotographs(requireContext());
    }
}
