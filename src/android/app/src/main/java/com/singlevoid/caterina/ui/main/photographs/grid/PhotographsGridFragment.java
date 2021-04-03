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

package com.singlevoid.caterina.ui.main.photographs.grid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.PhotographManager;

public class PhotographsGridFragment extends Fragment  {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private DataViewModel data;
    private View root;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_collection_grid, container, false);
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


    private void init() {
        initViewModel();
        setListeners();
    }


    private void initViewModel() {
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
    }


    private void setListeners() {
        data.getPhotographs(requireContext()).observe(getViewLifecycleOwner(),this::loadPhotographs);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void loadPhotographs(PhotographManager photographs) {
        data.getFilters().observe(getViewLifecycleOwner(), filters -> updateDataSet(photographs, filters) );
    }


    private void updateDataSet(PhotographManager photographs, FilterManager filters){
        if(getGrid().getAdapter() == null){
            getGrid().setAdapter( new PhotographsGridAdapter(getContext(), photographs, filters) );
        }
        else{
            ( (PhotographsGridAdapter) getGrid().getAdapter() ).notifyDataSetChanged();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VIEWS                                             //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private GridView getGrid() {
        return root.findViewById(R.id.CollectionGrid);
    }
}