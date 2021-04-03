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

package com.singlevoid.caterina.ui.filters.bar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.filters.bar.items.FilterBarItemAuthorView;
import com.singlevoid.caterina.ui.filters.bar.items.FilterBarItemCollectionView;
import com.singlevoid.caterina.ui.filters.bar.items.FilterBarItemLocationView;

import org.jetbrains.annotations.NotNull;

public class FilterBarFragment extends Fragment implements TextWatcher {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View root;
    private DataViewModel data;
    private FilterManager filters;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_filters_bar, container, false);
        return root;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        initDataViewModel();
        initValues();
        setListeners();
    }


    private void initDataViewModel() {
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    private void initValues() {
        getItemCollectionView().configure(this, R.id.navigation_filters_tags);
        getItemLocationView().configure(this, R.id.navigation_filters_location);
        getItemAuthorView().configure(this, R.id.navigation_filters_authors);
    }


    private void setListeners() {
        data.getFilters().observe(requireActivity(), this::updateFilters);
        getOptionsButton().setOnClickListener(this::showFilterOptions);
        getClearSearchBar().setOnClickListener(this::clearFilterText);
        getSearchBar().addTextChangedListener(this);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private EditText getSearchBar() {
        return root.findViewById(R.id.fragment_filters_bar_search_bar);
    }


    private View getFiltersView() {
        return requireActivity().findViewById(R.id.fragment_collection_filter_container);
    }


    private View getFiltersCard() {
        return getFiltersView().findViewById(R.id.fragment_collection_filter_card);
    }


    private ImageButton getOptionsButton() {
        return root.findViewById(R.id.fragment_filters_bar_image_filters);
    }


    private ImageView getClearSearchBar() {
        return root.findViewById(R.id.fragment_filters_bar_search_bar_clear);
    }


    private FilterBarItemCollectionView getItemCollectionView() {
        return root.findViewById(R.id.fragment_filters_bar_collection_view);
    }


    private FilterBarItemLocationView getItemLocationView() {
        return root.findViewById(R.id.fragment_filters_bar_location_view);
    }


    private FilterBarItemAuthorView getItemAuthorView() {
        return root.findViewById(R.id.fragment_filters_bar_author_view);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          BOOLEANS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean filterTextMatchesSearchBarText() {
        return !filters.getTextFilter().getText().equals(getSearchBar().getText().toString()) && getContext() != null;
    }


    private boolean searchTextIsEmpty() {
        return this.filters.getTextFilter().isDefault();
    }


    private boolean searchBarIsEmpty() {
        return getSearchBar().getText().toString().isEmpty();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void clearFilterText(View view){
        getSearchBar().setText("");
    }


    private void setSearchBarText(){
        if ( searchTextIsEmpty() )                 { getSearchBar().setText(""); }
        if ( filterTextMatchesSearchBarText() )    { getSearchBar().setText(filters.getTextFilter().getText()); }
    }

    private void updateModeViews(){
        getItemCollectionView().setFilter(filters.getTagFilter());
        getItemLocationView().setFilter(filters.getLocationFilter());
        getItemAuthorView().setFilter(filters.getAuthorFilter());
        setSearchBarText();
        updateVisibilityClearSearchBar();
    }


    private void updateFilters(@NotNull FilterManager filters) {
        this.filters = filters;
        this.updateModeViews();
        getSearchBar().addTextChangedListener(this);
    }


    private void updateVisibilityClearSearchBar() {
        if( searchBarIsEmpty() )   { getClearSearchBar().setVisibility(View.GONE); }
        else                        { getClearSearchBar().setVisibility(View.VISIBLE); }
    }


    @NotNull
    private NavController getNavHostController(){
        // THIS IS STUPID
        NavHostFragment fragment = (NavHostFragment) requireActivity()
                        .getSupportFragmentManager().getFragments().get(0)
                        .getChildFragmentManager().getFragments().get(0)
                        .getChildFragmentManager().findFragmentById(R.id.filters_host_fragment);

        return fragment.getNavController();
    }


    private void showFilterOptions(View view){
        navigateTo(R.id.navigation_filters);
    }


    public void navigateTo(int navigation){
        getNavHostController().navigate(navigation);
        animateShowFilter();
    }


    private void animateShowFilter(){
        if ( getFiltersView().getVisibility() != View.VISIBLE){
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);
            getFiltersView().setVisibility(View.VISIBLE);
            getFiltersCard().startAnimation(anim);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   TEXT WATCHER                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }


    @Override
    public void afterTextChanged(@NotNull Editable s) {
        if(s.toString().equals("") && filters.getTextFilter().isDefault()){
            return;
        }
        filters.getTextFilter().setText(s.toString());
        data.getFilters().postValue(filters);
    }
}
