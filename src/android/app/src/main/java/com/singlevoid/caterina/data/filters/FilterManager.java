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

public class FilterManager {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final ArrayList<FilterBase> filters = new ArrayList<>();
    private final FilterLocation location;
    private final FilterTag tag;
    private final FilterAuthor author;
    private final FilterText text;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterManager(){
        location = new FilterLocation();
        tag = new FilterTag();
        author = new FilterAuthor();
        text = new FilterText();

        filters.add(location);
        filters.add(tag);
        filters.add(author);
        filters.add(text);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterLocation getLocationFilter() {
        return location;
    }


    public FilterTag getTagFilter() {
        return tag;
    }


    public FilterAuthor getAuthorFilter() {
        return author;
    }


    public FilterText getTextFilter() {
        return text;
    }


    public void setToDefault() {
        for ( FilterBase filter: filters ) {
            filter.setToDefault();
        }
    }


    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs) {
        ArrayList<Photograph> filteredPhotographs = photographs;

        for ( FilterBase filter: filters) {
            filteredPhotographs = filter.filter(filteredPhotographs);
        }

        return filteredPhotographs;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   BOOLEANS                                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isDefault() {
        for (FilterBase filter: filters) {
            if ( !filter.isDefault() ) {
                return false;
            }
        }
        return true;
    }
}
