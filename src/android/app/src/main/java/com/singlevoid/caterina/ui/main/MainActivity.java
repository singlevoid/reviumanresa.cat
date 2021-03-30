package com.singlevoid.caterina.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.ui.photograph.PhotographDetailActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadNavigation();
        ifNotSavedInstanceGoToMap(savedInstanceState);
    }

    private void loadNavigation(){
        BottomNavigationView navView = findViewById(R.id.activty_main_nav_view);
        navView.setOnNavigationItemReselectedListener(item -> { });

        new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_map,
                R.id.navigation_collection).build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_host_fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavigationUI.setupWithNavController(navView, navController);
    }


    private void ifNotSavedInstanceGoToMap(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            navController.navigate(R.id.navigation_map);
        }
    }


    public void openPhotographDetail(Photograph photograph){
        Intent intent = new Intent(this, PhotographDetailActivity.class);
        intent.putExtra(getString(R.string.PHOTOGRAPH_EXTRA_MESSAGE), photograph);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up_long, R.anim.slide_down_long);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void filterNavigateTo(View container, Integer id){
        NavController navController = Navigation.findNavController(container);
        navController.navigate(id);
        super.onPostResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}