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


package com.singlevoid.caterina.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.TypedValue;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;

public class AppUtils {


    public static int getThemeColor(@NotNull Context context, int color){
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(color, value, true);
        return value.data;
    }


    public static boolean isLocationAllowed(Context context){
        return (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }


}
