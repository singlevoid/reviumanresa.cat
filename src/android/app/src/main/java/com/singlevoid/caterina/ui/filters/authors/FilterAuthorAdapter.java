package com.singlevoid.caterina.ui.filters.authors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.filters.FilterViewHolder;
import com.singlevoid.caterina.utils.AppUtils;

import java.util.ArrayList;

public class FilterAuthorAdapter extends RecyclerView.Adapter<FilterViewHolder>{


    private final Context context;
    private final FilterManager filterManager;
    private final DataViewModel dataViewModel;
    private final ArrayList<FilterViewHolder> items = new ArrayList<>();


    public FilterAuthorAdapter(FilterManager filterManager,
                            Context context,
                            DataViewModel dataViewModel) {
        this.filterManager = filterManager;
        this.context = context;
        this.dataViewModel = dataViewModel;
    }


    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new FilterViewHolder(LayoutInflater.from(viewGroup.getContext())
                   .inflate(R.layout.filter_base_option, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder item, int position) {
        initValues(item, position);
        items.add(item);
        updateAllOptionsStatus();
    }


    private void initValues(FilterViewHolder item, int position){
        item.setFilterOption(filterManager.getAuthor().getOptions().get(position));
        item.setText(item.getFilterOption().getValue());
        item.getCard().setOnClickListener((v) -> this.changeStatus(item));
    }


    @Override
    public int getItemCount() {
        return filterManager.getAuthor().getOptions().size();
    }


    private void updateAllOptionsStatus() {
        for (FilterViewHolder item: items) {
            updateItem(item);
        }
    }


    private void updateItem(FilterViewHolder item){
        if(item.getFilterOption().isActive()) {item.setActive(context);}
        else                                  {item.setInactive(context);}
    }


    private void changeStatus(FilterViewHolder filterOption) {
        filterOption.getFilterOption().invertStatus();
        updateAllOptionsStatus();
        updateViewModel();
    }


    private void updateViewModel(){
        dataViewModel.updateFilters(filterManager);
    }
}
