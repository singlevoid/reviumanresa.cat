package com.singlevoid.caterina.ui.filters.sort;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;

public class FilterSortAdapter extends RecyclerView.Adapter<FilterSortAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View root;
        public TextView text;

        public ViewHolder(View view) {
            super(view);
            root = view;
            text = view.findViewById(R.id.filter_base_text_title);
        }
    }
}
