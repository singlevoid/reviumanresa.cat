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

package com.singlevoid.caterina.ui.main.near;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NearItemFragment extends Fragment {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private View root;
    private Photograph photograph;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.item_near_photograph, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init(){
        loadTitle();
        loadDistance();
        loadImage();
        setListeners();
    }


    private void loadTitle() {
        getTitleView().setText(photograph.getTitle());
    }


    private void loadDistance() {
        getDistanceView().setText(requireContext().getString(R.string.distance, (int) Math.round(photograph.getDistance())));
    }


    private void loadImage() {
        Glide.with(this).load(photograph.highQualityReference()).into(getImageView());
    }


    private void setListeners() {
        getImageView().setOnClickListener(this::itemClicked);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          UI VIEWS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private ImageView getImageView() {
        return root.findViewById(R.id.item_near_photograph_image);
    }


    private TextView getTitleView() {
        return root.findViewById(R.id.item_near_photograph_text_title);
    }


    private TextView getDistanceView() {
        return root.findViewById(R.id.item_near_photograph_distance);
    }


    @NotNull
    private MainActivity getMainActivity() {
        return ((MainActivity) requireActivity());
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setPhotograph(Photograph photograph) {
        this.photograph = photograph;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void itemClicked(View view) {
        getMainActivity().openPhotographDetail(photograph);
    }
}
