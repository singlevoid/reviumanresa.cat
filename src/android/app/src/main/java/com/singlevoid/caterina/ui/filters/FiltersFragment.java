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

package com.singlevoid.caterina.ui.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;

import org.jetbrains.annotations.NotNull;


public class FiltersFragment extends Fragment {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View root;
    private DataViewModel data;
    private FilterManager filters;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_filters, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              INIT                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        initDataViewModel();
        setListeners();
    }


    private void setListeners(){
        data.getFilters().observe(getViewLifecycleOwner(), this::updateFilters);
        getLocationCard().setOnClickListener(this::navigateToLocation);
        getTagsCard().setOnClickListener(this::navigateToTags);
        getAuthorsCard().setOnClickListener(this::navigateToAuthors);
        getResetButton().setOnClickListener(this::clearFilters);
        getCloseButton().setOnClickListener(this::close);
    }


    private void initDataViewModel() {
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View getLocationCard() {
        return root.findViewById(R.id.fragment_filters_card_location);
    }


    private View getTagsCard() {
        return root.findViewById(R.id.fragment_filters_card_tag);
    }


    private View getAuthorsCard() {
        return root.findViewById(R.id.fragment_filters_card_author);
    }


    private TextView getResetButton() {
        return root.findViewById(R.id.fragment_filters_reset);
    }


    private ImageView getCloseButton() {
        return root.findViewById(R.id.fragment_filters_close);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void updateFilters(FilterManager manager){
        this.filters = manager;
        updateVisibilityReset(manager);
    }


    private void clearFilters(View view) {
        filters.setToDefault();
        data.getFilters().postValue(filters);
    }


    private void updateVisibilityReset(@NotNull FilterManager manager) {
        if(manager.isDefault()){
            getResetButton().setVisibility(View.GONE);
        }
        else{
            getResetButton().setVisibility(View.VISIBLE);
        }
    }


    private void navigateToTags(View view) {
        NavController navController = Navigation.findNavController(root);
        navController.navigate(R.id.navigation_filters_tags);
    }


    private void navigateToLocation(View view) {
        NavController navController = Navigation.findNavController(root);
        navController.navigate(R.id.navigation_filters_location);
    }


    private void navigateToAuthors(View view){
        NavController navController = Navigation.findNavController(root);
        navController.navigate(R.id.navigation_filters_authors);
    }

    private void close(View view){
        View parent = requireActivity().findViewById(R.id.fragment_collection_filter_container);
        parent.setVisibility(View.GONE);
    }
}
