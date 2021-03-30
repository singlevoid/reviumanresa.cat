package com.singlevoid.caterina.ui.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.main.MainActivity;


public class FiltersFragment extends Fragment {


    private View root;
    private DataViewModel data;
    private FilterManager filters;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_filters, container, false);
        return root;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataViewModel();
        setListeners();
    }


    private void initDataViewModel() {
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        data.getFilters(requireActivity()).observe(getViewLifecycleOwner(), this::updateFilters);
        getResetButton().setOnClickListener(this::clearFilters);
    }


    private View getLocationCard() {
        return root.findViewById(R.id.fragment_filters_card_location);
    }


    private View getSortCard() {
        return root.findViewById(R.id.fragment_filters_card_sort);
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


    private void updateFilters(FilterManager manager){
        this.filters = manager;
        updateVisibilityReset(manager);
    }


    private void clearFilters(View view) {
        filters.setToDefault();
        data.getFilters(requireActivity()).postValue(filters);
    }


    private void updateVisibilityReset(FilterManager manager) {
        if(manager.isDefault()){
            getResetButton().setVisibility(View.GONE);
        }
        else{
            getResetButton().setVisibility(View.VISIBLE);
        }

    }


    private void setListeners(){
        getLocationCard().setOnClickListener(this::navigateToLocation);
        getSortCard().setOnClickListener(this::navigateToSort);
        getTagsCard().setOnClickListener(this::navigateToTags);
        getAuthorsCard().setOnClickListener(this::navigateToAuthors);

    }

    private void navigateToTags(View view) {
        ((MainActivity) requireActivity()).filterNavigateTo(root, R.id.navigation_filters_tags);
    }

    private void navigateToSort(View view) {
        ((MainActivity) requireActivity()).filterNavigateTo(root, R.id.navigation_filters_sort);
    }

    private void navigateToLocation(View view) {
        ((MainActivity) requireActivity()).filterNavigateTo(root, R.id.navigation_filters_location);
    }

    private void navigateToAuthors(View view){
        ((MainActivity) requireActivity()).filterNavigateTo(root, R.id.navigation_filters_authors);
    }

}
