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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterLocation extends FilterBase {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static int MATCH_ALL = 0;
    public static int MATCH_GEOLOCATED = 50;
    public static int MATCH_NO_GEOLOCATED = 51;

    private int filterMode = MATCH_ALL;
    private Integer distanceRadius = null;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean isDefault(){
        return isFilterModeMatchAll();
    }


    @Override
    public void setToDefault() {
        super.setToDefault();
        setFilterMode(MATCH_ALL);
        setDistanceRadius(null);
    }


    @NotNull
    @Override
    public ArrayList<Photograph> filter (@NotNull ArrayList<Photograph> photographs) {
        if ( isFilterModeGeolocation() )        { return getPhotographsGeolocated(photographs); }
        if ( isFilterModeNonGeolocation() )     { return getPhotographsNoGeolocated(photographs); }
        return photographs;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public int getFilterMode() {
        return filterMode;
    }


    public void setFilterMode(int mode) {
        filterMode = mode;
    }


    public Integer getDistanceRadius() {
        return distanceRadius;
    }


    public void setDistanceRadius(Integer distance) {
        distanceRadius = distance;
    }


    @NotNull
    private ArrayList<Photograph> getPhotographsGeolocated(@NotNull ArrayList<Photograph> photographs) {
        ArrayList<Photograph> filter = new ArrayList<>();

        for ( Photograph photograph: photographs) {
            if ( photographIsLocatedAndWithinRadius(photograph) ) {
                filter.add(photograph);
            }
        }
        return filter;
    }



    @NotNull
    private ArrayList<Photograph> getPhotographsNoGeolocated(@NotNull ArrayList<Photograph> photographs) {
        ArrayList<Photograph> filter = new ArrayList<>();

        for ( Photograph photograph: photographs) {
            if ( !photograph.isLocalized() ) {
                filter.add(photograph);
            }
        }
        return filter;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   BOOLEANS                                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean isFilterModeMatchAll(){
        return getFilterMode() == MATCH_ALL;
    }


    private boolean isFilterModeGeolocation(){
        return getFilterMode() == MATCH_GEOLOCATED;
    }


    private boolean isFilterModeNonGeolocation() {
        return getFilterMode() == MATCH_NO_GEOLOCATED;
    }


    private boolean photographIsLocatedAndWithinRadius(@NotNull Photograph photograph) {
        if( photograph.isLocalized() ){
            if( distanceRadius == null) { return true; }
            return distanceRadius != null && photograph.getDistance() <= distanceRadius;
        }
        return false;
    }
}
