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
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.main.MainActivity;

public class FilterAuthorFragment extends Fragment {

    private View root;

    private DataViewModel data;
    private RecyclerView recycler;
    private FilterManager filters;

    private ImageView back;
    private TextView reset;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.filter_author, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initViewModel();
        initRecycler();
        setListeners();
    }


    private void initViews() {
        back = root.findViewById(R.id.filter_authors_back);
        reset = root.findViewById(R.id.filter_authors_reset);
    }


    private void initRecycler(){
        recycler = root.findViewById(R.id.filter_authors_recycler);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(5,
                                  StaggeredGridLayoutManager.HORIZONTAL));
        recycler.setHasFixedSize(true);
    }


    private void initViewModel(){
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        data.getFilters(requireContext()).observe(getViewLifecycleOwner(), this::updateFilters);
    }


    private void updateFilters(FilterManager filters){
        this.filters = filters;
        updateVisibilityReset();
        data.getAuthors(requireContext()).observe(getViewLifecycleOwner(), this::loadAuthors);
    }


    private void loadAuthors(AuthorManager manager){
        loadAuthorsToFilterOptions(manager, filters);
        updateRecycler();
    }


    private void updateRecycler(){
        if(recycler.getAdapter() == null){
            recycler.setAdapter(new FilterAuthorAdapter(filters, requireContext(), data));
        }
        recycler.getAdapter().notifyDataSetChanged();
    }


    private void loadAuthorsToFilterOptions(AuthorManager authors, FilterManager filters){
        if(filters.getAuthor().getOptions().isEmpty()){
            filters.getAuthor().setAuthors(authors.getAuthors());
        }
    }


    private void setListeners() {
        data.getFilters(requireContext()).observe(getViewLifecycleOwner(), this::updateFilters);
        back.setOnClickListener(this::backToFilters);
        reset.setOnClickListener(this::resetFilters);
    }


    private void backToFilters(View view){
        ((MainActivity) requireActivity()).filterNavigateTo(view, R.id.navigation_filters);
    }


    private void resetFilters(View view) {
        filters.getAuthor().setToDefault();
        data.updateFilters(filters);
    }


    private void updateVisibilityReset(){
        if(filters.getAuthor().isDefault())     { reset.setVisibility(View.INVISIBLE); }
        else                                    { reset.setVisibility(View.VISIBLE); }
    }

}
