package com.singlevoid.caterina.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.TypedValue;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class AppUtils {

    public static int getThemeColor(Context context, int color){
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(color, value, true);
        return value.data;
    }

    public static boolean isLocationAllowed(Context context){
        return (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }


}
