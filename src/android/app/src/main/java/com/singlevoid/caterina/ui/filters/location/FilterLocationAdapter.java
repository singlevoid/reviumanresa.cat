package com.singlevoid.caterina.ui.filters.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterLocation;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.filters.FilterOption;
import com.singlevoid.caterina.ui.filters.FilterViewHolder;
import com.singlevoid.caterina.utils.AppUtils;

import java.util.ArrayList;

public class FilterLocationAdapter extends RecyclerView.Adapter<FilterViewHolder>{

    private final Context context;
    private final FilterManager filterManager;
    private final FilterLocation filter;
    private final DataViewModel dataViewModel;
    private final ArrayList<FilterViewHolder> items = new ArrayList<>();;

    public FilterLocationAdapter(FilterManager filterManager,
                                 Context context,
                                 DataViewModel dataViewModel) {

        this.filterManager = filterManager;
        this.filter = filterManager.getFilterLocation();
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
        item.hideIcon();
        items.add(item);

        updateOptionsStatus();
    }

    @Override
    public int getItemCount() {
        return filter.getOptions().size();
    }

    private void changeStatus( FilterOption filterOption) {
        this.filter.setStatus(filterOption, !filterOption.isActive());
        updateOptionsStatus();
        dataViewModel.updateFilters(filterManager);
    }

    private void updateOptionsStatus() {
        for (FilterViewHolder item : items) { updateItem(item); }
    }

    private void updateItem(FilterViewHolder item){
        if (item.getFilterOption().isActive())  { setItemActive(item); }
        else                                    { setItemInactive(item); }
    }

    private void setItemInactive(FilterViewHolder item) {
        item.getCard().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorBackground));
        item.getText().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColor));
    }

    private void setItemActive(FilterViewHolder item){
        item.getCard().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorPrimary));
        item.getText().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColorPrimary));
    }

}
