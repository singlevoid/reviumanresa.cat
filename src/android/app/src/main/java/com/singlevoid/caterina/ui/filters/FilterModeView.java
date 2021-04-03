package com.singlevoid.caterina.ui.filters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.card.MaterialCardView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.utils.AppUtils;

public class FilterModeView extends ConstraintLayout {


    private final Context context;
    private View root;
    private int mode;
    private boolean isActive = false;


    public FilterModeView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }


    public FilterModeView(@NonNull Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        initView();
    }


    public void configureFilterMode(int mode, int string){
        this.mode = mode;
        getTextView().setText(context.getString(string));
    }


    public void setActive(){
        isActive = true;
        getCardView().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorPrimary));
        getTextView().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
    }


    public void setInactive(){
        isActive = false;
        getCardView().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorBackground));
        getTextView().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColor));
    }


    public int getFilterMode(){
        return mode;
    }


    private void initView(){
        root = inflate(getContext(), R.layout.filter_base_option, this);
        getIconView().setVisibility(View.GONE);
    }


    private TextView getTextView(){
        return root.findViewById(R.id.filter_base_text_title);
    }


    private MaterialCardView getCardView(){
        return root.findViewById(R.id.filter_base_card);
    }


    private ImageView getIconView(){
        return root.findViewById(R.id.filter_base_image_icon);
    }


    public boolean isActive(){
        return isActive;
    }

}
