package com.singlevoid.caterina.ui.filters.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.main.MainActivity;

public class FilterLocationFragment extends Fragment {


    private View root;
    private View back;
    private View reset;


    private RecyclerView recycler;
    private DataViewModel data;
    private FilterManager filters;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.filter_location, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initValues();
        setListeners();
    }


    private void initValues(){
        data =  new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        back = root.findViewById(R.id.filter_location_back);
        reset = root.findViewById(R.id.filter_location_reset);
        recycler = root.findViewById(R.id.filter_location_recycler);

        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycler.setHasFixedSize(false);
    }


    private void setListeners(){
        data.getFilters(requireContext()).observe(getViewLifecycleOwner(), this::updateFilters);
        back.setOnClickListener(this::navigateToFilters);
        reset.setOnClickListener(this::resetFilters);
    }


    private void resetFilters(View view) {
        filters.getFilterLocation().setToDefault();
        data.updateFilters(filters);
    }


    private void navigateToFilters(View view) {
        ((MainActivity) requireActivity()).filterNavigateTo(root, R.id.navigation_filters);
    }


    private void updateFilters(FilterManager filters) {
        this.filters = filters;
        updateVisibilityReset();
        if(recycler.getAdapter() == null){
            recycler.setAdapter(new FilterLocationAdapter(filters, requireContext(), data));
        }
        recycler.getAdapter().notifyDataSetChanged();
    }


    private void updateVisibilityReset(){
        if(filters.getFilterLocation().isDefault())     { reset.setVisibility(View.INVISIBLE); }
        else                                            { reset.setVisibility(View.VISIBLE); }
    }
}
