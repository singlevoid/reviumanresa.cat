package com.singlevoid.caterina.ui.filters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterOption;
import com.singlevoid.caterina.utils.AppUtils;

public class FilterViewHolder extends RecyclerView.ViewHolder {


    private final View root;
    private final TextView text;
    private final ImageView icon;
    private final MaterialCardView card;
    private FilterOption option;


    public FilterViewHolder(View view) {
        super(view);
        root = view;
        text = view.findViewById(R.id.filter_base_text_title);
        icon = view.findViewById(R.id.filter_base_image_icon);
        card = view.findViewById(R.id.filter_base_card);
    }


    public View getRootView()               {return root;}
    public TextView getText()               {return text;}
    public ImageView getIcon()              {return icon;}
    public MaterialCardView getCard()       {return card;}
    public FilterOption getFilterOption()   {return option;}

    public void setFilterOption(FilterOption option){ this.option = option;}

    public void hideIcon()      {icon.setVisibility(View.GONE);}
    public void showIcon()      {icon.setVisibility(View.VISIBLE);}
    public void setRemoveIcon() {icon.setImageResource(R.drawable.ic_remove_black_24dp);}
    public void setAddIcon()    {icon.setImageResource(R.drawable.ic_add_black_24dp);}


    public void setText(String text) {
        getText().setText(text);
    }


    public void setIconColor(int color) {
        icon.setColorFilter(color);
    }


    public void setActive(Context context){
        getCard().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorPrimary));
        getText().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
        setIconColor(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
        setRemoveIcon();
    }


    public void setInactive(Context context){
        getCard().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorBackground));
        getText().setTextColor(AppUtils.getThemeColor(context,android.R.attr.textColor));
        setIconColor(AppUtils.getThemeColor(context,android.R.attr.textColor));
        setAddIcon();
    }
}
