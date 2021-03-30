package com.singlevoid.caterina.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.AppValues;

public class AppTheme {

    public static Integer THEME_DEFAULT = 0;
    public static Integer THEME_LIGHT = R.style.Theme_Caterina;

    private SharedPreferences prefs;

    public Integer getTheme(Context context){
        prefs = context.getSharedPreferences(context.getString(R.string.PREFERENCES_KEY), Context.MODE_PRIVATE);
        return prefs.getInt(context.getString(R.string.PREFERENCE_THEME), THEME_DEFAULT);
    }


    public Boolean isNightUsed(Context context){
        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;

    }

}
