package com.singlevoid.caterina.ui.main.photographs.list;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;

public class PhotographListItem extends RecyclerView.ViewHolder{


    private final View root;


    public PhotographListItem(@NonNull View root) {
        super(root);
        this.root = root;
    }


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
}
