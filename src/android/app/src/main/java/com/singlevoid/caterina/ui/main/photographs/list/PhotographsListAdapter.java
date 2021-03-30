package com.singlevoid.caterina.ui.main.photographs.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhotographsListAdapter extends RecyclerView.Adapter<PhotographListItem>
implements PhotographManager.LocalizationListener{


    private PhotographListItem item;
    private final FilterManager filters;
    private final PhotographManager dataSet;
    private final Context context;
    private Photograph photograph;


    public PhotographsListAdapter(PhotographManager dataSet, Context context, FilterManager filters) {
        this.dataSet = dataSet;
        this.context = context;
        this.filters = filters;
        dataSet.localizePhotographs(context);
        dataSet.addLocationListener(this);
    }


    @Override
    public void onLocalized() {
        notifyDataSetChanged();
    }


    @NotNull
    @Override
    public PhotographListItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_collection_list, viewGroup, false);
        return new PhotographListItem(view);
    }


    @Override
    public int getItemCount() {
        return getPhotographs().size();
    }


    @Override
    public void onBindViewHolder(@NotNull PhotographListItem item, final int position) {
        this.item = item;
        photograph = getPhotographs().get(position);
        loadImage();
        loadTitle();
        loadDescription();
        loadDistance();
        setListeners();
    }


    private void loadImage(){
        Glide.with(this.context)
                .asBitmap()
                .load(photograph.lowQualityReference())
                .into(item.getImageView());
    }


    private void loadTitle(){
        if(photograph.getTitle() != null){
            item.getTitleView().setText(photograph.getTitle());
        }
    }


    private void loadDescription(){
        if(photograph.getDescription() != null){
            item.getDescriptionView().setText(photograph.getDescription());
        }
    }


    private void loadDistance(){
        if(photograph.isLocalized() && photograph.getDistance() <= 5000){
            item.getDistanceView().setVisibility(View.VISIBLE);
            item.getExploreButton().setVisibility(View.VISIBLE);
            item.getDistanceView().setText(context.getResources().getString(R.string.distance,
                    (int) Math.round(photograph.getDistance())));
        }
        else{
            item.getDistanceView().setVisibility(View.GONE);
            item.getExploreButton().setVisibility(View.GONE);
        }
    }


    private ArrayList<Photograph> getPhotographs(){
        return filters.filter(dataSet.getAll());
    }


    private void setListeners(){
        item.getImageView().setOnClickListener(this::openDetail);
        item.getTitleView().setOnClickListener(this::openDetail);
        item.getDescriptionView().setOnClickListener(this::openDetail);
        item.getExploreButton().setOnClickListener(this::showOnMap);
        item.getShareButton().setOnClickListener(this::sharePhotograph);
        item.getBrowserButton().setOnClickListener(this::openInBrowser);
    }


    private void openDetail(View view){
        ((MainActivity) context).openPhotographDetail(photograph);
    }


    private void showOnMap(View view) {
        Bundle bundle = new Bundle();
        bundle.putFloat("Latitude", photograph.getLatitude());
        bundle.putFloat("Longitude", photograph.getLongitude());
        Navigation.findNavController(view).navigate(R.id.action_navigation_notifications_to_navigation_map, bundle);
    }


    private void sharePhotograph(View view){
        photograph.SharePhotograph(this.context);
    }


    private void openInBrowser(View view) {
        photograph.openInBrowser(context);
    }
}


