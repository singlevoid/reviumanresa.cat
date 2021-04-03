////////////////////////////////////////////////////////////////////////////////////////////////////
//                                      LICENSE                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                //
// Copyright [2020] [Joan Albert Espinosa Muns]                                                   //
//                                                                                                //
// Licensed under the Apache License, Version 2.0 (the "License")                                 //
// you may not use this file except in compliance with the License.                               //
// You may obtain a copy of the License at                                                        //
//                                                                                                //
// http://www.apache.org/licenses/LICENSE-2.0                                                     //
//                                                                                                //
// Unless required by applicable law or agreed to in writing, software                            //
// distributed under the License is distributed on an "AS IS" BASIS,                              //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                       //
// See the License for the specific language governing permissions and                            //
// limitations under the License.                                                                 //
//                                                                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

package com.singlevoid.caterina.ui.filters.bar.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.card.MaterialCardView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterBase;
import com.singlevoid.caterina.ui.filters.bar.FilterBarFragment;
import com.singlevoid.caterina.utils.AppUtils;


public abstract class FilterBarItemView extends ConstraintLayout {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    protected final Context context;
    protected FilterBarFragment fragment;
    protected final View root;
    protected int navigate;
    protected boolean isActive;
    protected FilterBase filter;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterBarItemView(@NonNull Context context) {
        super(context);
        this.context = context;
        root = inflate(getContext(), R.layout.filter_base_bar, this);
        root.setOnClickListener(this::clicked);
    }


    public FilterBarItemView(@NonNull Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        root = inflate(getContext(), R.layout.filter_base_bar, this);
        root.setOnClickListener(this::clicked);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    ABSTRACTS                                               //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public abstract void update();

    public abstract String getName(int quantity);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public TextView getTextView() {
        return root.findViewById(R.id.filter_base_bar_text);
    }


    public MaterialCardView getCardView() {
        return root.findViewById(R.id.filter_bar_base_card);
    }


    public ImageView getImageButton() {
        return root.findViewById(R.id.filter_base_bar_icon);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setFilter(FilterBase filter){
        this.filter = filter;
        update();
    }


    public FilterBase getFilter(){
        return filter;
    }


    public void setNavigate(int navigate){
        this.navigate = navigate;
    }


    public int getNavigate(){
        return navigate;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void configure(FilterBarFragment fragment, int navigate){
        this.fragment = fragment;
        setNavigate(navigate);
    }


    public void clicked(View view){
        fragment.navigateTo(getNavigate());
    }


    public void setActive(){
        isActive = true;
        getCardView().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorPrimary));
        getTextView().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
        getImageButton().setColorFilter(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
    }


    public void setInactive(){
        isActive = false;
        getCardView().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.windowBackgroundFallback));
        getTextView().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColor));
        getImageButton().setColorFilter(AppUtils.getThemeColor(context, android.R.attr.textColor));
    }
}
