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

package com.singlevoid.caterina.ui.filters.tags;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.card.MaterialCardView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.filters.FilterTag;
import com.singlevoid.caterina.data.tag.TagManager;
import com.singlevoid.caterina.ui.filters.FilterModeView;
import com.singlevoid.caterina.ui.main.MainActivity;
import com.singlevoid.caterina.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterTagFragment extends Fragment {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private View root;
    private final ArrayList<FilterModeView> filterMode = new ArrayList<>();
    private DataViewModel data;
    private FilterManager filters;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.filter_tags, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        initViewModel();
        configureViewModes();
        initRecycler();
        setListeners();
    }


    private void initViewModel() {
        data =  new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    private void configureViewModes() {
        getMatchAllButton().configureFilterMode(FilterTag.MATCH_ALL, R.string.match_all);
        getMatchAnyButton().configureFilterMode(FilterTag.MATCH_ANY, R.string.match_any);

        filterMode.add(getMatchAllButton());
        filterMode.add(getMatchAnyButton());
    }


    private void initRecycler(){
        getRecycler().setLayoutManager(new StaggeredGridLayoutManager(5,
                StaggeredGridLayoutManager.HORIZONTAL));
        getRecycler().setHasFixedSize(false);
    }


    private void setListeners(){
        data.getFilters().observe(getViewLifecycleOwner(), this::updateFilters);
        backButton().setOnClickListener(this::backPressed);
        getResetButton().setOnClickListener(this::resetFilters);
        getCloseButton().setOnClickListener(this::close);

        getMatchAllButton().setOnClickListener(this::modeClicked);
        getMatchAnyButton().setOnClickListener(this::modeClicked);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private ImageView getCloseButton(){
        return root.findViewById(R.id.filter_tags_close);
    }


    private ImageView backButton(){
        return root.findViewById(R.id.filter_tags_back);
    }


    private RecyclerView getRecycler(){
        return root.findViewById(R.id.filter_tags_recycler);
    }


    private TextView getResetButton(){
        return root.findViewById(R.id.filter_tags_reset);
    }


    private FilterModeView getMatchAllButton(){
        return root.findViewById(R.id.filter_tags_match_all);
    }


    private FilterModeView getMatchAnyButton(){
        return root.findViewById(R.id.filter_tags_match_any);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void modeClicked(View view){
        filters.getTagFilter().setFilterMode(((FilterModeView) view).getFilterMode());
        updateFiltersMode();
        data.updateFilters(filters);
    }


    private void updateFiltersMode(){
        for(FilterModeView filter: filterMode) {
            if(filter.getFilterMode() == filters.getTagFilter().getFilterMode()){
                filter.setActive();
            }
            else{
                filter.setInactive();
            }
        }
    }


    private void close(View view){
        View parent = requireActivity().findViewById(R.id.fragment_collection_filter_container);
        parent.setVisibility(View.GONE);
    }


    private void backPressed(View view) {
        ((MainActivity) requireActivity()).filterNavigateTo(view, R.id.navigation_filters);
    }


    private void resetFilters(View view) {
        filters.getTagFilter().setToDefault();
        updateFiltersMode();
        data.updateFilters(filters);
    }


    private void updateFilters(FilterManager filters) {
        this.filters = filters;
        updateVisibilityReset();
        updateFiltersMode();
        data.getTags(requireContext()).observe(getViewLifecycleOwner(), this::loadTags);
    }


    private void loadTags(TagManager tags){
        loadTags(tags, filters);
        if(getRecycler().getAdapter() == null){
            getRecycler().setAdapter(new FilterTagAdapter(filters, requireContext(), data));
        }
        getRecycler().getAdapter().notifyDataSetChanged();
    }


    private void loadTags(TagManager tags, @NotNull FilterManager filters){
        if(filters.getTagFilter().getOptions().isEmpty()){
            filters.getTagFilter().setTags(tags.getAllTags());
        }
    }


    private void updateVisibilityReset(){
        if(filters.getTagFilter().isDefault())    { getResetButton().setVisibility(View.INVISIBLE); }
        else                                      { getResetButton().setVisibility(View.VISIBLE); }
    }
}
