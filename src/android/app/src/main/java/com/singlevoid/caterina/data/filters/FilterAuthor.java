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

import com.singlevoid.caterina.data.author.Author;
import com.singlevoid.caterina.data.photograph.Photograph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterAuthor extends FilterBase {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static final int MATCH_ANY = 40;
    private int filterMode = MATCH_ANY;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs) {
        if  (getActiveFilters().isEmpty())  {return photographs;}
        else                                {return  getPhotographsThatMatchesAnyFilter(photographs);}
    }


    @Override
    public boolean isDefault(){
        return super.isDefault() && filterMode == MATCH_ANY;
    }


    @Override
    public void setToDefault() {
        super.setToDefault();
        filterMode = MATCH_ANY;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setAuthorFilterOptions(@NotNull ArrayList<Author> authors){
        getOptions().clear();
        for( Author author: authors ){
            getOptions().add( new FilterOption(author.getName(), author.getId()) );
        }
    }


    public void setFilterMode(int mode){
        filterMode = mode;
    }


    @NotNull
    private ArrayList<Photograph> getPhotographsThatMatchesAnyFilter(@NotNull ArrayList<Photograph> photographs){
        ArrayList<Photograph> filtered = new ArrayList<>();

        for(Photograph photograph: photographs) {
            if( photographAuthorMatchesActiveAuthorFilter(photograph) ){
                filtered.add(photograph);
            }
        }
        return filtered;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          BOOLEANS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean photographAuthorMatchesActiveAuthorFilter(Photograph photograph) {
        for (FilterOption option: getActiveFilters()){
            if(photographAuthorMatchesID(photograph, option.getId())){
                return true;
            }
        }
        return false;
    }


    private boolean photographAuthorMatchesID(@NotNull Photograph photograph, String id){
        return photograph.getAuthor() != null && photograph.getAuthor().equals(id);
    }
}
