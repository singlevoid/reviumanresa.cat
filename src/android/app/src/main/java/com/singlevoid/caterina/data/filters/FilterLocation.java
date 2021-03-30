package com.singlevoid.caterina.data.filters;

import android.content.Context;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.photograph.Photograph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterLocation extends FilterBase {

    public static String GEOLOCATION = null;
    public static String NO_GEOLOCATION = null;
    private final Context context;

    FilterLocation(Context context){super(); this.context = context; initOptions(); }

    public boolean isDefault(){
        return getActiveFilter() == null;
    }

    private void initOptions(){
        GEOLOCATION = context.getString(R.string.filter_only_geolocalized);
        NO_GEOLOCATION = context.getString(R.string.filter_only_non_localized);

        getOptions().add(new FilterOption(GEOLOCATION));
        getOptions().add(new FilterOption(NO_GEOLOCATION));
    }

    public void setStatus(FilterOption option, Boolean status){
        if(!status){ setToDefault(); }
        else{
            setAllFiltersToInactive();
            option.setActive(true);
        }
    }

    public String getActiveFilter(){
        for(FilterOption option: getOptions()){
            if(option.isActive()) {return option.getValue();}
        }
        return null;
    }

    public ArrayList<Photograph> filter (@NotNull ArrayList<Photograph> photographs) {
        ArrayList<Photograph> filtered = new ArrayList<>();

        if(getActiveFilter() == null){
            return photographs;
        }

        for (Photograph photograph : photographs) {
            if (getOptionByValue(GEOLOCATION).isActive()) {
                if (photograph.isLocalized()) {
                    filtered.add(photograph);
                }
            }
            else if (getOptionByValue(NO_GEOLOCATION).isActive()){
                if (!photograph.isLocalized()) {
                    filtered.add(photograph);
                }
            }
        }
        return filtered;
    }
}
