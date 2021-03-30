package com.singlevoid.caterina.ui.main.near;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;


public class NearViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Photograph> photographs;

    public NearViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        photographs = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        NearItemFragment fragment = new NearItemFragment();
        fragment.setPhotograph(photographs.get(position));
        return fragment;
    }

    @Override
    public int getItemCount() {
        return photographs.size();
    }

    public void setPhotographs(ArrayList<Photograph> photographs){
        this.photographs = photographs;
        notifyDataSetChanged();
    }
}
