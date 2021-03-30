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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.main.MainActivity;

public class FilterBarFragment extends Fragment {


    private View root;
    private DataViewModel data;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_filters_bar, container, false);
        return root;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataViewModel();
        initValues();
        setListeners();
    }


    private void initDataViewModel() {
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    private void initValues(){
        getRecycler().setLayoutManager(new LinearLayoutManager(requireActivity(),
                                                               LinearLayoutManager.HORIZONTAL,
                                                  false));
    }


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


    private RecyclerView getRecycler() {
        return root.findViewById(R.id.fragment_filters_bar_recycler);
    }


    private ImageView getClearSearchBar() {
        return root.findViewById(R.id.fragment_filters_bar_search_bar_clear);
    }


    private void setListeners(){
        data.getFilters(requireContext()).observe(requireActivity(), this::updateFilters);
        getOptionsButton().setOnClickListener(this::showFilterOptions);
        getClearSearchBar().setOnClickListener(this::clearFilterText);
    }


    private void clearFilterText(View view){
        getSearchBar().setText("");
    }


    private void updateFilters(FilterManager manager) {
        if(manager.getText().isDefault()){
            getSearchBar().setText("");
        }
        getSearchBar().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTextFilter(manager, s.toString());
            }
        });

        if(getRecycler().getAdapter() == null){
            getRecycler().setAdapter(new FilterBarAdapter((MainActivity) requireActivity(),
                    requireContext(), getFiltersView(), data));
        }
        else{
            getRecycler().getAdapter().notifyDataSetChanged();
        }
    }


    private void updateTextFilter(FilterManager manager, String text) {
        if(text.equals("")){
            if(manager.getText().isDefault()){
                return;
            }
            manager.getText().setToDefault();
            data.getFilters(requireContext()).postValue(manager);
            updateVisibilityClearSearchBar(manager);
        }
        else{
            manager.getText().setText(text);
            updateVisibilityClearSearchBar(manager);
            data.getFilters(requireContext()).postValue(manager);
            getRecycler().getAdapter().notifyDataSetChanged();
        }
    }


    private void updateVisibilityClearSearchBar(FilterManager manager) {
        if(manager.getText().getText().isEmpty()){
            getClearSearchBar().setVisibility(View.GONE);
        }
        else{
            getClearSearchBar().setVisibility(View.VISIBLE);
        }
    }


    private void showFilterOptions(View view){
        ((MainActivity) requireActivity()).filterNavigateTo(requireActivity().findViewById(R.id.filters_host_fragment), R.id.navigation_filters);
        animateShowFilter();
    }


    private void animateShowFilter(){
        if ( getFiltersView().getVisibility() != View.VISIBLE){
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);
            getFiltersView().setVisibility(View.VISIBLE);
            getFiltersCard().startAnimation(anim);
        }
    }
}
