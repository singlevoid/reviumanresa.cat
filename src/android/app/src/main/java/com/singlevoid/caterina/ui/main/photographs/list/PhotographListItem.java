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

package com.singlevoid.caterina.ui.main.photographs.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.ui.main.MainActivity;

public class PhotographListItem extends RecyclerView.ViewHolder{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private Context context;
    private final View root;
    private Photograph photograph;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public PhotographListItem(@NonNull View root) {
        super(root);
        this.root = root;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        loadImageView();
        loadTitle();
        loadDescription();
        loadDistance();
        setListeners();
    }


    private void loadImageView(){
        Glide.with(this.context)
                .asBitmap()
                .load(photograph.lowQualityReference())
                .into(getImageView());
    }


    private void loadTitle() {
        if(photograph.getTitle() != null) {
            getTitleView().setText(photograph.getTitle());
        }
    }


    private void loadDescription() {
        if(photograph.getDescription() != null){
            getDescriptionView().setText(photograph.getDescription());
        }
    }


    private void loadDistance() {
        if(photograph.isLocalized() && photograph.getDistance() <= 5000){
            getDistanceView().setVisibility(View.VISIBLE);
            getExploreButton().setVisibility(View.VISIBLE);
            getDistanceView().setText(context.getResources().getString(R.string.distance, (int) Math.round(photograph.getDistance())));
        }
        else{
            getDistanceView().setVisibility(View.GONE);
            getExploreButton().setVisibility(View.GONE);
        }
    }


    private void setListeners() {
        getImageView().setOnClickListener(this::openPhotographDetail);
        getTitleView().setOnClickListener(this::openPhotographDetail);
        getDescriptionView().setOnClickListener(this::openPhotographDetail);
        getExploreButton().setOnClickListener(this::showPhotographOnMap);
        getShareButton().setOnClickListener(this::sharePhotograph);
        getBrowserButton().setOnClickListener(this::openPhotographInWebSite);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public ImageView getImageView() {
        return root.findViewById(R.id.item_collection_list_item_image);
    }


    public TextView getTitleView() {
        return root.findViewById(R.id.item_collection_list_item_text_title);
    }


    public TextView getDescriptionView() {
        return root.findViewById(R.id.item_collection_list_item_text_description);
    }


    public TextView getDistanceView() {
        return root.findViewById(R.id.item_collection_list_item_text_distance);
    }


    public ImageButton getExploreButton() {
        return root.findViewById(R.id.item_collection_list_item_button_explore);
    }


    public ImageButton getShareButton() {
        return root.findViewById(R.id.item_collection_list_item_button_share);
    }


    public ImageButton getBrowserButton() {
        return root.findViewById(R.id.item_collection_list_item_button_browser);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private MainActivity getMainActivity() {
        return  ((MainActivity) context);
    }


    public void setPhotograph(Context context, Photograph photograph) {
        this.context = context;
        this.photograph = photograph;
        init();
    }


    private void openPhotographDetail(View view) {
        getMainActivity().openPhotographDetail(photograph);
    }


    private void showPhotographOnMap(View view) {
        photograph.SharePhotograph(this.context);
    }


    private void sharePhotograph(View view) {
        Bundle bundle = new Bundle();
        bundle.putFloat("Latitude", photograph.getLatitude());
        bundle.putFloat("Longitude", photograph.getLongitude());
        Navigation.findNavController(view).navigate(R.id.action_navigation_notifications_to_navigation_map, bundle);
    }


    private void openPhotographInWebSite(View view) {
        photograph.openInBrowser(context);
    }
}
