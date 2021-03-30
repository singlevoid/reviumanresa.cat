package com.singlevoid.caterina.data.photograph;

public class GeoFilter extends Filter {

    public static final int ALL = 0;
    public static final int ONLY_GEOLOCALIZED = 1;
    public static final int ONLY_NO_GEOLOCALIZED = 2;
    private int status = ALL;

    GeoFilter(){}


}
