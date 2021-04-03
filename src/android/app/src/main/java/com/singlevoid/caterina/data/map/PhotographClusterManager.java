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

package com.singlevoid.caterina.data.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;
import com.singlevoid.caterina.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

public class PhotographClusterManager extends ClusterManager<PhotographMarker>
        implements ClusterManager.OnClusterItemClickListener<PhotographMarker> {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final Context context;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public PhotographClusterManager(Context context, GoogleMap map) {
        super(context, map);
        this.context = context;
        setRenderer(new PhotographClusterRenderer<>(context, map, this));
        setOnClusterItemClickListener(this);
    }


    @Override
    public boolean onClusterItemClick(@NotNull PhotographMarker item) {
        ( (MainActivity) context ).openPhotographDetail( item.getPhotograph() );
        return false;
    }
}