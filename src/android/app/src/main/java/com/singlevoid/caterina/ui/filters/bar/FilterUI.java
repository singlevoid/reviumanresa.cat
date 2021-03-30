package com.singlevoid.caterina.ui.filters.bar;

import com.singlevoid.caterina.data.filters.FilterBase;

public class FilterUI{


    private final FilterBase option;
    private final int navigation;
    private final int name;


    FilterUI(FilterBase option, int navigation, int name){
        this.option = option;
        this.navigation = navigation;
        this.name = name;
    }


    public int getName(){
        return name;
    }


    public int getNavigation() {
        return navigation;
    }


    public FilterBase getFilter() {
        return option;
    }
}