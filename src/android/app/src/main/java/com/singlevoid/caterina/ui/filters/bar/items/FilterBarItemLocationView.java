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
import com.singlevoid.caterina.data.filters.FilterLocation;



public class FilterBarItemLocationView extends FilterBarItemView {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public FilterBarItemLocationView(@NonNull Context context) {
        super(context);
    }


    public FilterBarItemLocationView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public String getName(int quantity) {
        return context.getString(R.string.distance, quantity);
    }



    @Override
    public FilterLocation getFilter(){
        return (FilterLocation) filter;
    }


    @Override
    public void update() {
        if ( filterModeIsMatchAll() )           { setViewToMatchAll(); }
        if ( filterModeIsMatchGeolocated() )    { setViewToMatchGeolocated(); }
        if ( filterModeIsMatchNoGeolocated() )  { setViewToMatchNoGeolocated(); }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          BOOLEANS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean filterModeIsMatchAll() {
        return getFilter().getFilterMode() == FilterLocation.MATCH_ALL;
    }


    private boolean filterModeIsMatchGeolocated() {
        return getFilter().getFilterMode() == FilterLocation.MATCH_GEOLOCATED;
    }


    private boolean filterModeIsMatchNoGeolocated() {
        return getFilter().getFilterMode() == FilterLocation.MATCH_NO_GEOLOCATED;
    }


    private boolean filterHasDistanceRadius() {
        return getFilter().getDistanceRadius() != null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void setViewToMatchAll() {
        setInactive();
        getTextView().setText(context.getString(R.string.location));
    }


    private void setViewToMatchNoGeolocated() {
        setActive();
        getTextView().setText(context.getString(R.string.filter_only_non_localized));
    }


    private void setViewToMatchGeolocated() {
        setActive();
        if(filterHasDistanceRadius()){
            getTextView().setText(getName(getFilter().getDistanceRadius()));
        }
        else{
            getTextView().setText(context.getString(R.string.filter_only_geolocalized));
        }
    }
}
