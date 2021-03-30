package com.singlevoid.caterina.ui.filters.bar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.main.MainActivity;

import java.util.ArrayList;

public class FilterBarAdapter extends RecyclerView.Adapter<FilterBarViewItem>{


    private final Context context;
    private final MainActivity activity;
    private final View root;
    private final ArrayList<FilterUI> UItems = new ArrayList<>();
    private final ArrayList<FilterBarViewItem> items = new ArrayList<>();


    FilterBarAdapter(MainActivity activity, Context context,
                     View filterHost, DataViewModel data){
        this.activity = activity;
        this.context = context;
        this.root = filterHost;
        initDataViewModel(data);
    }


    private void initDataViewModel(DataViewModel data) {
        data.getFilters(context).observe(activity, this::updateItems);
    }


    private View getFilterFragment() {
        return root.findViewById(R.id.filters_host_fragment);
    }


    private View getFiltersCard() {
        return root.findViewById(R.id.fragment_collection_filter_card);
    }


    private void updateItems(FilterManager filters) {
        loadItemsIfEmpty(filters);
        for(FilterBarViewItem item: items) {
            updateItem(item);
        }
    }


    private void loadItemsIfEmpty(FilterManager filters) {
        if(UItems.isEmpty()){
            UItems.add(new FilterUI(filters.getFilterLocation(), R.id.navigation_filters_location, R.plurals.location));
            UItems.add(new FilterUI(filters.getTag(), R.id.navigation_filters_tags, R.plurals.tags ));
            UItems.add(new FilterUI(filters.getAuthor(), R.id.navigation_filters_authors, R.plurals.authors ));
        }
    }


    private void updateItem(FilterBarViewItem item){
        item.updateItem(context);
    }


    @NonNull
    @Override
    public FilterBarViewItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new FilterBarViewItem(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.filter_base_bar, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull FilterBarViewItem item, int position) {
        setItemData(item, position);
    }


    private void setItemData(FilterBarViewItem item, int position) {
        item.setName(UItems.get(position).getName());
        item.setNavigation(UItems.get(position).getNavigation());
        item.setFilter(UItems.get(position).getFilter());
        item.getCardView().setOnClickListener((v) -> navigateTo(item));
        item.updateItem(context);
        items.add(item);
    }


    private void navigateTo(FilterBarViewItem item) {
        activity.filterNavigateTo(getFilterFragment(), item.getNavigation());
        animateShowFilter();
    }


    private void animateShowFilter(){
        if ( root.getVisibility() != View.VISIBLE){
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            root.setVisibility(View.VISIBLE);
            getFiltersCard().startAnimation(anim);
        }
    }


    @Override
    public int getItemCount() {
        return UItems.size();
    }
}


