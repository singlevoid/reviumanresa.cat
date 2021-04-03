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

package com.singlevoid.caterina.ui.filters.tags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.filters.FilterOption;
import com.singlevoid.caterina.data.filters.FilterTag;
import com.singlevoid.caterina.ui.filters.FilterViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterTagAdapter extends RecyclerView.Adapter<FilterViewHolder>{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final Context context;
    private final FilterManager filterManager;
    private final FilterTag filter;
    private final DataViewModel dataViewModel;
    private final ArrayList<FilterViewHolder> items = new ArrayList<>();


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterTagAdapter(@NotNull FilterManager filterManager, Context context, DataViewModel dataViewModel) {
        this.filterManager = filterManager;
        this.filter = filterManager.getTagFilter();
        this.context = context;
        this.dataViewModel = dataViewModel;
    }


    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        return new FilterViewHolder(LayoutInflater.from(viewGroup.getContext())
                                    .inflate(R.layout.filter_base_option, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder item, int position) {

        item.setFilterOption(filter.getOptions().get(position));
        item.getText().setText(item.getFilterOption().getValue());
        item.getCard().setOnClickListener((v) -> changeStatus(item.getFilterOption()));
        items.add(item);
        updateOptionsStatus();
    }


    @Override
    public int getItemCount() { return filter.getOptions().size(); }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void updateOptionsStatus() {
        for (FilterViewHolder item: items) {
            updateItem(item);
        }
    }


    private void updateItem(@NotNull FilterViewHolder item){
        if(item.getFilterOption().isActive()) {item.setActive(context);}
        else                                  {item.setInactive(context);}
    }


    private void changeStatus( FilterOption filterOption) {
        this.filter.setStatus(filterOption, !filterOption.isActive());
        updateOptionsStatus();
        dataViewModel.updateFilters(filterManager);
    }
}
