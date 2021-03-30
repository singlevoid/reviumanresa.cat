package com.singlevoid.caterina.ui.filters.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;

public class FilterSortFragment extends Fragment {

    private View root;
    private DataViewModel dataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.filter_sort, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        View back = root.findViewById(R.id.filter_sort_back);
        dataViewModel =  new ViewModelProvider(requireActivity()).get(DataViewModel.class);

        dataViewModel.getFilters(requireContext()).observe(getViewLifecycleOwner(), this::loadFilter);
        back.setOnClickListener((v) -> navController.navigate(R.id.navigation_filters));

    }

    private void loadFilter(FilterManager filterManager) {
    }
}
