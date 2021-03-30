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

import java.util.ArrayList;

public class FilterTagFragment extends Fragment {


    private View root;
    private final ArrayList<FilterModeView> filterMode = new ArrayList<>();

    private DataViewModel data;
    private FilterManager filters;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.filter_tags, container, false);
        new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        close(root);
                        return true;
                    }
                });
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initValues();
        initRecycler();
        setListeners();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).popBackStack(R.id.navigation_filters, true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }


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


    private void initValues(){
        data =  new ViewModelProvider(requireActivity()).get(DataViewModel.class);

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
        data.getFilters(requireContext()).observe(getViewLifecycleOwner(), this::updateFilters);
        backButton().setOnClickListener(this::backPressed);
        getResetButton().setOnClickListener(this::resetFilters);
        getCloseButton().setOnClickListener(this::close);

        getMatchAllButton().setOnClickListener(this::modeClicked);
        getMatchAnyButton().setOnClickListener(this::modeClicked);
    }


    private void modeClicked(View view){
        filters.getTag().setFilterMode(((FilterModeView) view).getFilterMode());
        updateFiltersMode();
        data.updateFilters(filters);
    }


    private void updateFiltersMode(){
        for(FilterModeView filter: filterMode) {
            if(filter.getFilterMode() == filters.getTag().getFilterMode()){
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
        filters.getTag().setToDefault();
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


    private void loadTags(TagManager tags, FilterManager filters){
        if(filters.getTag().getOptions().isEmpty()){
            filters.getTag().setTags(tags.getTags());
        }
    }


    private void updateVisibilityReset(){
        if(filters.getTag().isDefault())    { getResetButton().setVisibility(View.INVISIBLE); }
        else                                { getResetButton().setVisibility(View.VISIBLE); }
    }

}
