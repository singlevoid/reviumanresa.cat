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

package com.singlevoid.caterina.ui.filters.bar.items;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.singlevoid.caterina.R;


public class FilterBarItemCollectionView  extends FilterBarItemView{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterBarItemCollectionView(@NonNull Context context) {
        super(context);
    }


    public FilterBarItemCollectionView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public String getName(int quantity){
        return context.getResources().getQuantityString(R.plurals.tags, quantity, quantity);
    }


    @Override
    public void update(){
        if(getActiveFiltersCount() == 0){
            getTextView().setText(getName(1));
            setInactive();
        }

        else if(getActiveFiltersCount() == 1){
            getTextView().setText(getFilter().getActiveFilters().get(0).getValue());
            setActive();
        }

        else{
            getTextView().setText(getName(getActiveFiltersCount()));
            setActive();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public int getActiveFiltersCount() {
        return filter.getActiveFilters().size();
    }
}
