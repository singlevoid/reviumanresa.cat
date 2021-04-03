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

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MapLocation implements Parcelable {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private float lat;
    private float lng;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public MapLocation() { }


    public MapLocation(float lat, float lng) {
        setLat(lat);
        setLng(lng);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setLat(float lat) {
        this.lat = lat;
    }


    public void setLng(float lng) {
        this.lng = lng;
    }


    public float getLat() {
        return lat;
    }


    public float getLng() {
        return lng;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    PARCELABLE                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public static final Creator<MapLocation> CREATOR = new Creator<MapLocation>() {
        @NotNull
        @Contract("_ -> new")
        @Override
        public MapLocation createFromParcel(Parcel in) {
            return new MapLocation(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        @Override
        public MapLocation[] newArray(int size) {
            return new MapLocation[size];
        }
    };


    protected MapLocation(@NotNull Parcel in) {
        lat = in.readFloat();
        lng = in.readFloat();
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        parcel.writeFloat(lat);
        parcel.writeFloat(lng);
    }
}