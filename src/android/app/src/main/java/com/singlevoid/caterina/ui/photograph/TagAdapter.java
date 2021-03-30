package com.singlevoid.caterina.ui.photograph;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.tag.Tag;

import java.util.ArrayList;

public class TagAdapter extends BaseAdapter {

    private final ArrayList<Tag> tags;
    private final Context context;

    public TagAdapter(Context context, ArrayList<Tag> tags){
        this.context = context;
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int i) {
        return tags.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint({"ViewHolder", "InflateParams"}) View tagView = inflater.inflate(R.layout.item_tag, null);
        TextView name = tagView.findViewById(R.id.item_tag_text_title);
        Tag tag = (Tag) getItem(i);
        name.setText(tag.getName());
        return tagView;
    }
}