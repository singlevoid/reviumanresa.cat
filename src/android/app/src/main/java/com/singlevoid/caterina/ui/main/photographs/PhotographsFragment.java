package com.singlevoid.caterina.ui.main.photographs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.singlevoid.caterina.R;

public class PhotographsFragment extends Fragment {

    private View root;
    private View background;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_collection, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initValues();
        setListeners();
    }

    private void initValues(){
        viewPager = root.findViewById(R.id.pager);
        tabLayout = root.findViewById(R.id.CollectionTabLayout);
        background = root.findViewById(R.id.fragment_collection_filter_container);

        viewPager.setAdapter(new PhotographPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager, this::tabStyle).attach();
    }

    private void setListeners(){
        background.setOnClickListener(this::backPressed);
    }

    private void backPressed(View view) {
        background.setVisibility(View.GONE);

    }

    private void tabStyle(TabLayout.Tab tab, int i) {
        if(i == 0){
            tab.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_grid_on_black_24dp, null));
        }
        if(i == 1){
            tab.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_list_black_24dp, null));
        }
    }

}
