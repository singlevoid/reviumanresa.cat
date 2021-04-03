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

package com.singlevoid.caterina.ui.main.photographs.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhotographsListAdapter extends RecyclerView.Adapter<PhotographListItem>
                                    implements PhotographManager.LocalizationListener{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final FilterManager filters;
    private final PhotographManager dataSet;
    private final Context context;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public PhotographsListAdapter(@NotNull PhotographManager dataSet, Context context, FilterManager filters) {
        this.dataSet = dataSet;
        this.context = context;
        this.filters = filters;
        dataSet.localizePhotographs(context);
        dataSet.addLocationListener(this);
    }


    @Override
    public void onLocalized() {
        notifyDataSetChanged();
    }


    @NotNull
    @Override
    public PhotographListItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_collection_list, viewGroup, false);
        return new PhotographListItem(view);
    }


    @Override
    public int getItemCount() {
        return getPhotographs().size();
    }


    @Override
    public void onBindViewHolder(@NotNull PhotographListItem item, final int position) {
        item.setPhotograph(context, getPhotographs().get(position) );
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private ArrayList<Photograph> getPhotographs() {
        return filters.filter(dataSet.getAll());
    }
}


