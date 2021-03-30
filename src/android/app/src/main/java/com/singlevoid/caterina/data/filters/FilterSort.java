package com.singlevoid.caterina.data.filters;

import android.content.Context;

import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;

public class FilterSort {

    private final ArrayList<FilterOption> options;
    private final Context context;

    FilterSort(Context context){
        this.context = context;
        options = new ArrayList<>();
    }

    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs){
        return photographs;
    }
}
