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

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.singlevoid.caterina.R;

import org.jetbrains.annotations.NotNull;

public class PhotographClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final Context context;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public PhotographClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }


    @Override
    protected boolean shouldRenderAsCluster(@NotNull Cluster<T> cluster) {
        return cluster.getSize() > 5;
    }


    @Override
    protected void onClusterItemRendered(@NotNull T clusterItem, @NonNull Marker marker){
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        super.onClusterItemRendered(clusterItem, marker);
    }


    @Override
    protected int getColor(int clusterSize) {
        return this.context.getColor(R.color.primary);
    }
}