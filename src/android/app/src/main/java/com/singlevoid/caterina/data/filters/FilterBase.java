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

package com.singlevoid.caterina.data.filters;

import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;

public abstract class FilterBase {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final ArrayList<FilterOption> options = new ArrayList<>();


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    ABSTRACTS                                               //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public abstract ArrayList<Photograph> filter(ArrayList<Photograph> photograph);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setToDefault() {
        setAllFiltersToInactive();
    }



    public boolean isDefault() {
        for( FilterOption option: getOptions() ) {
            if( option.isActive() ) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<FilterOption> getOptions() {
        return options;
    }


    public void setAllFiltersToInactive() {
        for( FilterOption option: options ) {
            option.setActive(false);
        }
    }


    public ArrayList<FilterOption> getActiveFilters() {
        ArrayList<FilterOption> active = new ArrayList<>();

        for( FilterOption option: getOptions() ) {
            if( option.isActive() ) {
                active.add(option);
            }
        }
        return active;
    }
}
