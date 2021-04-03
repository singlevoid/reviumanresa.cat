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
import com.singlevoid.caterina.data.tag.Tag;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterTag extends FilterBase {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static int MATCH_ALL = 30;
    public static int MATCH_ANY = 31;

    private int mode = MATCH_ALL;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs) {
        if  ( getActiveFilters().isEmpty() )  { return photographs; }
        if          ( mode == MATCH_ANY )     { return matchAnyFilter(photographs); }
        else if     ( mode == MATCH_ALL )     { return matchAllFilter(photographs); }
        else                                  { return new ArrayList<>(); }
    }


    @Override
    public boolean isDefault() {
        return super.isDefault() && mode == MATCH_ALL;
    }


    @Override
    public void setToDefault() {
        super.setToDefault();
        mode = MATCH_ALL;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setStatus(@NotNull FilterOption option, Boolean status){
        option.setActive(status);
    }


    public void setFilterMode(int mode) {
        this.mode = mode;
    }


    public int getFilterMode() {
        return mode;
    }


    public void setTags(@NotNull ArrayList<Tag> tags){
        getOptions().clear();
        for(Tag tag: tags){
            getOptions().add(new FilterOption(tag.getName(), tag.getId()));
        }
    }


    @NotNull
    private ArrayList<Photograph> matchAllFilter(@NotNull ArrayList<Photograph> photographs){
        ArrayList<Photograph> filtered = new ArrayList<>();

        for(Photograph photograph: photographs) {
            if ( photographContainsAllTags(photograph) ){
                filtered.add(photograph);
            }
        }
        return filtered;
    }


    @NotNull
    private ArrayList<Photograph> matchAnyFilter(@NotNull ArrayList<Photograph> photographs){
        ArrayList<Photograph> filteredPhotographs = new ArrayList<>();

        for(Photograph photograph: photographs) {
            if ( photographContainsAnyTag(photograph) ) {
                filteredPhotographs.add(photograph);
            }
        }
        return filteredPhotographs;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          BOOLEANS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean photographContainsAnyTag(Photograph photograph) {
        for ( FilterOption option: getActiveFilters() ) {
            if ( photograph.getTags().contains(option.getId()) ){
                return true;
            }
        }
        return false;
    }


    private boolean photographContainsAllTags(Photograph photograph) {
        for (FilterOption option: getActiveFilters() ) {
            if ( !photograph.getTags().contains(option.getId()) ) {
                return false;
            }
        }
        return true;
    }
}
