package com.singlevoid.caterina.ui.filters.tags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.filters.FilterOption;
import com.singlevoid.caterina.data.filters.FilterTag;
import com.singlevoid.caterina.ui.filters.FilterViewHolder;
import com.singlevoid.caterina.utils.AppUtils;

import java.util.ArrayList;

public class FilterTagAdapter extends RecyclerView.Adapter<FilterViewHolder>{


    private final Context context;
    private final FilterManager filterManager;
    private final FilterTag filter;
    private final DataViewModel dataViewModel;
    private final ArrayList<FilterViewHolder> items = new ArrayList<>();


    public FilterTagAdapter(FilterManager filterManager,
                            Context context,
                            DataViewModel dataViewModel) {
        this.filterManager = filterManager;
        this.filter = filterManager.getTag();
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

        item.setFilterOption(filter.getOptions().get(position));
        item.getText().setText(item.getFilterOption().getValue());
        item.getCard().setOnClickListener((v) -> changeStatus(item.getFilterOption()));
        items.add(item);
        updateOptionsStatus();


    }


    @Override
    public int getItemCount() { return filter.getOptions().size(); }


    private void updateOptionsStatus() {
        for (FilterViewHolder item: items) { updateItem(item); }
    }


    private void updateItem(FilterViewHolder item){
        if(item.getFilterOption().isActive()) {item.setActive(context);}
        else                                  {item.setInactive(context);}
    }


    private void changeStatus( FilterOption filterOption) {
        this.filter.setStatus(filterOption, !filterOption.isActive());
        updateOptionsStatus();
        dataViewModel.updateFilters(filterManager);
    }
}
