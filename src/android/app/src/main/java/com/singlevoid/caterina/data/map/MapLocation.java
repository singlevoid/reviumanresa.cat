package com.singlevoid.caterina.data.map;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class MapLocation implements Parcelable {

    private float lat;
    private float lng;

    public MapLocation(){}
    public MapLocation(float lat, float lng){ this.lat = lat; this.lng = lng; }

    protected MapLocation(Parcel in) {
        lat = in.readFloat();
        lng = in.readFloat();
    }

    public static final Creator<MapLocation> CREATOR = new Creator<MapLocation>() {
        @Override
        public MapLocation createFromParcel(Parcel in) {
            return new MapLocation(in);
        }

        @Override
        public MapLocation[] newArray(int size) {
            return new MapLocation[size];
        }
    };

    public void setLat(float lat){this.lat = lat;}
    public void setLng(float lng){ this.lng = lng;}

    public boolean isLocalized() {
        return Objects.isNull(this.getLng());
    }

    public float getLat(){ return lat; }
    public float getLng(){ return lng; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(lat);
        parcel.writeFloat(lng);
    }
}