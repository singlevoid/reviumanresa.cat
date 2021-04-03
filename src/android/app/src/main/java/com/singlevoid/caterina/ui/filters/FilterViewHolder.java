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

package com.singlevoid.caterina.ui.filters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterOption;
import com.singlevoid.caterina.utils.AppUtils;

public class FilterViewHolder extends RecyclerView.ViewHolder {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final View root;
    private FilterOption option;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterViewHolder(View view) {
        super(view);
        root = view;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public View getRootView() {
        return root;
    }


    public TextView getText() {
        return getRootView().findViewById(R.id.filter_base_text_title);
    }


    public ImageView getIcon() {
        return getRootView().findViewById(R.id.filter_base_image_icon);
    }


    public MaterialCardView getCard() {
        return getRootView().findViewById(R.id.filter_base_card);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterOption getFilterOption() {
        return option;
    }


    public void setFilterOption(FilterOption option) {
        this.option = option;
    }


    public void setText(String text) {
        getText().setText(text);
    }


    public void setIconColor(int color) {
        getIcon().setColorFilter(color);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void hideIcon() {
        getIcon().setVisibility(View.GONE);
    }


    public void showIcon() {
        getIcon().setVisibility(View.VISIBLE);
    }


    public void setRemoveIcon() {
        getIcon().setImageResource(R.drawable.ic_remove_black_24dp);
    }


    public void setAddIcon() {
        getIcon().setImageResource(R.drawable.ic_add_black_24dp);
    }


    public void setActive(Context context) {
        getCard().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorPrimary));
        getText().setTextColor(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
        setIconColor(AppUtils.getThemeColor(context, android.R.attr.textColorHighlight));
        setRemoveIcon();
    }


    public void setInactive(Context context) {
        getCard().setCardBackgroundColor(AppUtils.getThemeColor(context, android.R.attr.colorBackground));
        getText().setTextColor(AppUtils.getThemeColor(context,android.R.attr.textColor));
        setIconColor(AppUtils.getThemeColor(context,android.R.attr.textColor));
        setAddIcon();
    }
}
