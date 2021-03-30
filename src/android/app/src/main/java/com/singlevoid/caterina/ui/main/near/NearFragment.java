package com.singlevoid.caterina.ui.main.near;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

public class NearFragment extends Fragment implements PhotographManager.LocalizationListener, Toolbar.OnMenuItemClickListener {

    private NearViewPagerAdapter adapter;
    private PhotographManager manager;
    private ViewPager2 viewPager;
    private DataViewModel dataViewModel;
    private TabLayout tabLayout;
    private TextView warningText;
    private Button locationButton;
    private View warningFrame;
    private Integer radius = 100;
    private static final Integer REQUEST_CODE = 40;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_near, container, false);
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.fragment_near_menu);
        toolbar.setOnMenuItemClickListener(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        viewPager = view.findViewById(R.id.nearViewPager);
        tabLayout = view.findViewById(R.id.NearTabLayout);
        warningText = view.findViewById(R.id.textView2);
        locationButton = view.findViewById(R.id.fragment_near_permission_button);
        warningFrame = view.findViewById(R.id.fragment_near_warning_frame);
        adapter = new NearViewPagerAdapter(this);
        locationButton.setOnClickListener(this::requestLocationPermission);

        if(!AppUtils.isLocationAllowed(requireContext())){ locationNotAllowed();}
        else{
            this.loadUI();
        }
    }


    private void emptyDataSet(){
        warningText.setVisibility(View.VISIBLE);
        locationButton.setVisibility(View.GONE);
        warningFrame.setVisibility(View.VISIBLE);
        warningText.setText(getString(R.string.near_empty, radius));
    }


    private void loadUI(){
        hideWarning();
        viewPager.setAdapter(adapter);
        dataViewModel.getPhotographs(requireContext()).observe(getViewLifecycleOwner(), this::loadPhotographs);
        new TabLayoutMediator(tabLayout, viewPager,this::tabStyle).attach();
    }

    private void locationNotAllowed(){
        warningText.setVisibility(View.VISIBLE);
        locationButton.setVisibility(View.VISIBLE);
        warningFrame.setVisibility(View.VISIBLE);
        warningText.setText(getString(R.string.near_permission));
    }


    private void hideWarning(){
        warningText.setVisibility(View.GONE);
        locationButton.setVisibility(View.GONE);
        warningFrame.setVisibility(View.GONE);
    }

    private void loadPhotographs(PhotographManager photographManager) {
        this.manager = photographManager;
        manager.addLocationListener(this);
        manager.localizePhotographs(getContext());
    }


    private void requestLocationPermission(View view){
        int REQUEST_CODE = 40;
        requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE);
    }

    private void setRadius(Integer radius){
        hideWarning();
        this.radius = radius;
        updateAdapter();
    }

    private void updateAdapter(){
        adapter.setPhotographs(manager.getNearPhotographs(radius));
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() == 0){ emptyDataSet(); }
        else{
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        }
    }

    private void tabStyle(TabLayout.Tab tab, int i) { }

    @Override
    public void onLocalized() { updateAdapter(); }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.loadUI();
            }
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.fragment_near_item_distance_50){ setRadius(50); }
        if(item.getItemId() == R.id.fragment_near_item_distance_100){ setRadius(100); }
        if(item.getItemId() == R.id.fragment_near_item_distance_150){ setRadius(150); }
        if(item.getItemId() == R.id.fragment_near_item_distance_200){ setRadius(200); }
        return false;
    }
}