package com.singlevoid.caterina.ui.main.photographs.grid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhotographsGridAdapter extends BaseAdapter {


    private final Context context;
    private final PhotographManager photographs;
    private final FilterManager filters;


    PhotographsGridAdapter(Context context, PhotographManager photographs, FilterManager filterManager){
        this.context = context;
        this.photographs = photographs;
        this.filters = filterManager;
    }


    @Override
    public int getCount() {
        return getPhotographs().size();
    }


    @Override
    public Object getItem(int i) {
        return getPhotographs().get(i);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Photograph photograph = (Photograph) getItem(i);
        View item = inflateGrid();
        ImageView image = item.findViewById(R.id.item_collection_grid_image);

        image.setOnClickListener((v) -> imageClicked(photograph));
        loadImage(image, photograph);
        return item;
    }


    @SuppressLint("InflateParams")
    private View inflateGrid(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.item_collection_grid, null);
    }


    private ArrayList<Photograph> getPhotographs(){
        return filters.filter(photographs.getAll());
    }


    private void imageClicked(Photograph photograph) {
        ((MainActivity) context).openPhotographDetail(photograph);
    }


    private void loadImage(ImageView view, @NotNull Photograph photograph){
        Glide.with(context)
                .load(photograph.lowQualityReference())
                .into(view);
    }
}