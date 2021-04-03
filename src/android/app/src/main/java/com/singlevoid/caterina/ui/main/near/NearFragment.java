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

package com.singlevoid.caterina.ui.main.near;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

public class NearFragment extends Fragment implements PhotographManager.LocalizationListener, AdapterView.OnItemSelectedListener {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View root;
    private PhotographManager manager;
    private DataViewModel dataViewModel;
    private Integer radius = 100;
    private static final Integer REQUEST_CODE = 40;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_near, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.manager.localizePhotographs(requireContext());
                this.updateAdapter();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        initSpinner();
        initViewModel();
        initViewPager();
        setListeners();
    }


    private void initViewModel() {
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
    }


    private void setListeners() {
        getLocationButton().setOnClickListener(this::requestLocationPermission);
        dataViewModel.getPhotographs(requireContext()).observe(getViewLifecycleOwner(), this::loadPhotographs);
    }


    private void initViewPager(){
        getViewPager().setAdapter(new NearViewPagerAdapter(this));
        new TabLayoutMediator(getTabLayout(), getViewPager(), this::tabStyle).attach();
    }


    private void initSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinner().setAdapter(adapter);
        getSpinner().setOnItemSelectedListener(this);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void emptyDataSet(){
        getLocationButton().setVisibility(View.GONE);
        getWarning().setVisibility(View.VISIBLE);
        getWarningTextView().setText(getString(R.string.near_empty, radius));
    }


    private void locationNotAllowed(){
        getLocationButton().setVisibility(View.VISIBLE);
        getWarning().setVisibility(View.VISIBLE);
        getWarningTextView().setText(getString(R.string.near_permission));
    }


    private void hideWarning(){
        getLocationButton().setVisibility(View.GONE);
        getWarning().setVisibility(View.GONE);
    }


    private void loadPhotographs(@NotNull PhotographManager manager) {
        this.manager = manager;
        manager.addLocationListener(this);
        manager.localizePhotographs(getContext());
    }


    private void requestLocationPermission(View view){
        int REQUEST_CODE = 40;
        requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE);
    }


    private void setRadius(Integer radius){
        this.radius = radius;
        updateAdapter();
    }


    private void updateAdapter(){
        if(!AppUtils.isLocationAllowed(requireContext())) { locationNotAllowed(); return;}
        getAdapter().setPhotographs(manager.getNearPhotographs(radius));
        getAdapter().notifyDataSetChanged();

        if (getAdapter().getItemCount() == 0)    { emptyDataSet(); }
        else                                     { showItems(); }
    }


    private void showItems(){
        hideWarning();
        getTabLayout().setTabGravity(TabLayout.GRAVITY_CENTER);
    }


    private void tabStyle(TabLayout.Tab tab, int i) { }


    @Override
    public void onLocalized() {
        updateAdapter();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          UI VIEWS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onItemSelected(@NotNull AdapterView<?> parent, View view, int position, long id) {
        setRadius(Integer.parseInt((String) parent.getItemAtPosition(position)));
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          UI VIEWS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private NearViewPagerAdapter getAdapter(){
        return (NearViewPagerAdapter) getViewPager().getAdapter();
    }


    private ViewPager2 getViewPager(){
        return root.findViewById(R.id.nearViewPager);
    }


    private View getWarning() {
        return root.findViewById(R.id.fragment_near_warning_frame);
    }


    private Spinner getSpinner() {
        return root.findViewById(R.id.fragment_near_spinner);
    }


    private TabLayout getTabLayout(){
        return root.findViewById(R.id.NearTabLayout);
    }


    private TextView getWarningTextView(){
        return root.findViewById(R.id.textView2);
    }

    private Button getLocationButton(){
        return root.findViewById(R.id.fragment_near_permission_button);
    }

}