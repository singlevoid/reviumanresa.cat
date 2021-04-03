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

package com.singlevoid.caterina.ui.filters.authors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.ui.filters.FilterViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterAuthorAdapter extends RecyclerView.Adapter<FilterViewHolder>{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final Context context;
    private final FilterManager filterManager;
    private final DataViewModel dataViewModel;
    private final ArrayList<FilterViewHolder> items = new ArrayList<>();


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterAuthorAdapter(FilterManager filterManager,
                            Context context,
                            DataViewModel dataViewModel) {
        this.filterManager = filterManager;
        this.context = context;
        this.dataViewModel = dataViewModel;
    }


    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new FilterViewHolder(LayoutInflater.from(viewGroup.getContext())
                   .inflate(R.layout.filter_base_option, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder item, int position) {
        initValues(item, position);
        items.add(item);
        updateAllOptionsStatus();
    }


    @Override
    public int getItemCount() {
        return filterManager.getAuthorFilter().getOptions().size();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void initValues(@NotNull FilterViewHolder item, int position){
        item.setFilterOption(filterManager.getAuthorFilter().getOptions().get(position));
        item.setText(item.getFilterOption().getValue());
        item.getCard().setOnClickListener((v) -> this.changeStatus(item));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void updateAllOptionsStatus() {
        for (FilterViewHolder item: items) {
            updateItem(item);
        }
    }


    private void updateItem(@NotNull FilterViewHolder item){
        if(item.getFilterOption().isActive()) {item.setActive(context);}
        else                                  {item.setInactive(context);}
    }


    private void changeStatus(@NotNull FilterViewHolder filterOption) {
        filterOption.getFilterOption().invertStatus();
        updateAllOptionsStatus();
        updateViewModel();
    }


    private void updateViewModel(){
        dataViewModel.updateFilters(filterManager);
    }
}
