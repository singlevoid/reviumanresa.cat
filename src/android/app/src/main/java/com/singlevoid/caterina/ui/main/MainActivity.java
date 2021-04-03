////////////////////////////////////////////////////////////////////////////////////////////////////
//                                      LICENSE                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                //
// Copyright [2020] [Joan Albert Espinosa Muns]                                                   //
//                                                                                                //
// Licensed under the Apache License, Version 2.0 (the "License")                                 //
// you may not use this file except in compliance with the License.                               //
// You may obtain a copy of the License at                                                        //
//                                                                                                //
// http://www.apache.org/licenses/LICENSE-2.0                                                     //
//                                                                                                //
// Unless required by applicable law or agreed to in writing, software                            //
// distributed under the License is distributed on an "AS IS" BASIS,                              //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                       //
// See the License for the specific language governing permissions and                            //
// limitations under the License.                                                                 //
//                                                                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

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

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadNavigation();

        if (savedInstanceState == null){
            getNavController().navigate(R.id.navigation_map);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private BottomNavigationView getBottomNavigationView(){
        return findViewById(R.id.activity_main_nav_view);
    }


    private NavHostFragment getHostFragment(){
        return (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_host_fragment);
    }


    @NotNull
    private NavController getNavController() {
        return getHostFragment().getNavController();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void loadNavigation(){
        getBottomNavigationView().setOnNavigationItemReselectedListener(item -> { });

        new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_map,
                R.id.navigation_collection).build();

        NavigationUI.setupWithNavController(getBottomNavigationView(), getNavController());
    }


    public void openPhotographDetail(Photograph photograph){
        Intent intent = new Intent(this, PhotographDetailActivity.class);
        intent.putExtra(getString(R.string.PHOTOGRAPH_EXTRA_MESSAGE), photograph);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up_long, R.anim.slide_down_long);
    }


    public void filterNavigateTo(View container, Integer id){
        super.onPostResume();
        NavController navController = Navigation.findNavController(container);
        navController.navigate(id);
        super.onPostResume();
    }
}