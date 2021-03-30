package com.singlevoid.caterina.ui.filters.bar;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterBase;
import com.singlevoid.caterina.utils.AppUtils;

public class FilterBarViewItem extends RecyclerView.ViewHolder {


    private final View root;
    private FilterBase filter;
    private int navigation;
    private int name;


    public FilterBarViewItem(View view) {
        super(view);
        root = view;
    }


    public void setFilter(FilterBase filter) {
        this.filter = filter;
    }


    public TextView getTextView() {
        return root.findViewById(R.id.filter_base_bar_text);
    }


    public MaterialCardView getCardView() {
        return root.findViewById(R.id.filter_bar_base_card);
    }


    public ImageView getImageButton() {
        return root.findViewById(R.id.filter_base_bar_icon);
    }


    public FilterBase getFilter() {
        return filter;
    }


    public int getNavigation() {
        return navigation;
    }


    public void setName(int name) {
        this.name = name;
    }

    public void setNavigation(int navigation){
        this.navigation = navigation;
    }

    public int getActiveFiltersCount() {
        return filter.getActiveFilters().size();
    }


    public void updateItem(Context context) {

        if(getActiveFiltersCount() == 0){
            getTextView().setText(getName(context, 1));
            setInactive(context);
        }

        else if(getActiveFiltersCount() == 1){
            getTextView().setText(getFilter().getActiveFilters().get(0).getValue());
            setActive(context);
        }

        else{
            getTextView().setText(getName(context, getActiveFiltersCount()));
            setActive(context);
        }
    }


    public void setActive(Context context){
        getCardView().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorPrimary));
        getTextView().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
        getImageButton().setColorFilter(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
    }


    public void setInactive(Context context){
        getCardView().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.windowBackgroundFallback));
        getTextView().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColor));
        getImageButton().setColorFilter(AppUtils.getThemeColor(context, android.R.attr.textColor));
    }


    public String getName(Context context, int quantity){
        return context.getResources().getQuantityString(name, quantity, quantity);
    }


}
