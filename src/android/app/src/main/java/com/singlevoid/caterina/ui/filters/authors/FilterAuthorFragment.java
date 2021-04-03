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

package com.singlevoid.caterina.ui.filters.authors;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.author.AuthorManager;
import com.singlevoid.caterina.data.filters.FilterAuthor;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.filters.FilterModeView;
import com.singlevoid.caterina.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

public class FilterAuthorFragment extends Fragment {


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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.filter_author, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          UI VIEWS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @NotNull
    private MainActivity getMainActivity() {
        return ( (MainActivity) requireActivity() );
    }


    private ImageView getBackButton() {
        return root.findViewById(R.id.filter_authors_back);
    }


    private ImageView getCloseButton() {
        return root.findViewById(R.id.filter_authors_close);
    }


    private FilterModeView getAuthorMatchMode() {
        return root.findViewById(R.id.filter_author_mode);
    }


    private TextView getResetButton() {
        return root.findViewById(R.id.filter_authors_reset);
    }


    private RecyclerView getRecyclerView() {
        return root.findViewById(R.id.filter_authors_recycler);
    }


    private View getParentView() {
        return requireActivity().findViewById(R.id.fragment_collection_filter_container);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              INIT                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        initFilterModes();
        initViewModel();
        initRecycler();
        setListeners();
    }


    private void initFilterModes() {
        getAuthorMatchMode().setActive();
        getAuthorMatchMode().configureFilterMode(FilterAuthor.MATCH_ANY, R.string.match_any);
    }


    private void initRecycler() {
        getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(5,
                                               StaggeredGridLayoutManager.HORIZONTAL));
    }


    private void initViewModel() {
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    private void setListeners() {
        data.getFilters().observe(getViewLifecycleOwner(), this::updateFilters);
        data.getAuthors(requireContext()).observe(getViewLifecycleOwner(), this::loadAuthors);

        getBackButton().setOnClickListener(this::backToFilters);
        getResetButton().setOnClickListener(this::resetFilters);
        getCloseButton().setOnClickListener(this::close);
        getAuthorMatchMode().setOnClickListener(this::setMatchAnyModeFilter);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          METHODS                                           //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void updateFilters(FilterManager filters) {
        this.filters = filters;
        updateRecycler();
        updateVisibilityReset();
    }


    private void updateRecycler() {
        if( getRecyclerView().getAdapter() == null ) {
            getRecyclerView().setAdapter(new FilterAuthorAdapter(filters, requireContext(), data));
        }
        getRecyclerView().getAdapter().notifyDataSetChanged();
    }


    private void loadAuthors(AuthorManager manager) {
        loadAuthorsToFilterOptions(manager, filters);
        updateRecycler();
    }


    private void loadAuthorsToFilterOptions(AuthorManager authors, @NotNull FilterManager filters) {
        if(filters.getAuthorFilter().getOptions().isEmpty()){
            filters.getAuthorFilter().setAuthorFilterOptions(authors.getAuthors());
        }
    }


    private void backToFilters(View view) {
        getMainActivity().filterNavigateTo(view, R.id.navigation_filters);
    }


    private void resetFilters(View view) {
        filters.getAuthorFilter().setToDefault();
        data.updateFilters(filters);
    }


    private void updateVisibilityReset() {
        if(filters.getAuthorFilter().isDefault())   { getResetButton().setVisibility(View.INVISIBLE); }
        else                                        { getResetButton().setVisibility(View.VISIBLE); }
    }


    private void close(View view) {
        getParentView().setVisibility(View.GONE);
    }


    private void setMatchAnyModeFilter(View view) {
        filters.getAuthorFilter().setFilterMode(FilterAuthor.MATCH_ANY);
    }
}
