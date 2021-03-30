package com.singlevoid.caterina.data.filters;

import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;

public abstract class FilterBase {

    private final ArrayList<FilterOption> options = new ArrayList<>();

    public abstract ArrayList<Photograph> filter(ArrayList<Photograph> photograph);
    public void setToDefault(){setAllFiltersToInactive();}
    public ArrayList<FilterOption> getOptions(){ return options; }

    public FilterOption getOptionByValue(String value){
        for(FilterOption option: options){ if(option.getValue().equals(value)){return option;}}
        return null;
    }

    public void setAllFiltersToInactive(){
        for(FilterOption option: options){option.setActive(false);}
    }

    public ArrayList<FilterOption> getActiveFilters(){
        ArrayList<FilterOption> active = new ArrayList<>();
        for(FilterOption option: getOptions()){ if(option.isActive()){active.add(option);}}
        return active;
    }

    public boolean isDefault(){
        for(FilterOption option: getOptions()){
            if(option.isActive()){ return false;}
        }
        return true;
    }

}

