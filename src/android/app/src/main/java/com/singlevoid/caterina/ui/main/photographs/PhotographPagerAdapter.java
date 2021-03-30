package com.singlevoid.caterina.ui.main.photographs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.singlevoid.caterina.ui.main.photographs.list.PhotographsListFragment;
import com.singlevoid.caterina.ui.main.photographs.grid.PhotographsGridFragment;

public class PhotographPagerAdapter extends FragmentStateAdapter {

    public PhotographPagerAdapter(@NonNull Fragment fragment) { super(fragment); }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)   { return new PhotographsGridFragment(); }
        else                { return new PhotographsListFragment(); }
    }

    @Override
    public int getItemCount() { return 2; }
}