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

package com.singlevoid.caterina.data.photograph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.singlevoid.caterina.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhotographManager {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final ArrayList<Photograph> photographs = new ArrayList<>();
    private LocalizationListener locationListener;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////



    public ArrayList<Photograph> getAll() {
        return photographs;
    }


    public void addPhotograph(Photograph photograph) {
        photographs.add(photograph);
    }


    public ArrayList<Photograph> getNearPhotographs(Integer radius){
        ArrayList<Photograph> near = new ArrayList<>();
        for (Photograph photograph: photographs){
            if(photograph.isLocalized()){
                if(photograph.getDistance() <= radius) {
                    near.add(photograph);
                }
            }
        }
        return near;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void addLocationListener(LocalizationListener listener){
        this.locationListener = listener;
    }


    public void calculateDistanceTo(Location point){
        for(Photograph photograph: photographs){
            if( photograph.isLocalized() ){
                photograph.setDistanceTo(point);
            }
        }
    }


    @SuppressLint("MissingPermission")
    public void localizePhotographs(Context context){
        if(AppUtils.isLocationAllowed(context)) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this::lastLocationListener);
        }
    }


    private void lastLocationListener(Location location) {
        if(location != null){
            calculateDistanceTo(location);
            locationListener.onLocalized();
        }
    }


    public PhotographManager parseQuery(@NotNull QuerySnapshot query){
        for (QueryDocumentSnapshot document : query){
            Photograph photograph = document.toObject(Photograph.class);
            photograph.setId(document.getId());
            addPhotograph(photograph);
        }
        return this;
    }


    public interface LocalizationListener {
        void onLocalized();
    }
}
