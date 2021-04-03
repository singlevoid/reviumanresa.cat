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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View root;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_collection, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init(){
        initViewPager();
        initListeners();
    }


    private void initViewPager() {
        getViewPager().setAdapter(new PhotographPagerAdapter(this));
        new TabLayoutMediator(getTabLayout(), getViewPager(), this::tabStyle).attach();
    }


    private void initListeners() {
        getBackground().setOnClickListener(this::backPressed);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View getBackground(){
        return root.findViewById(R.id.fragment_collection_filter_container);
    }


    private ViewPager2 getViewPager(){
        return root.findViewById(R.id.fragment_collection_view_pager);
    }


    private TabLayout getTabLayout(){
        return root.findViewById(R.id.fragment_collection_tab_layout);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void backPressed(View view) {
        getBackground().setVisibility(View.GONE);
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
