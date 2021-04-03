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

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.map.MapLocation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Photograph implements Parcelable{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private String id;
    private String title;
    private String description;
    private String author;
    private String source;
    private MapLocation location;
    public PhotographDate date;
    private ArrayList<String> tags;
    private double distance;
    private String license;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public Photograph() {}


    public Photograph(String title, String description, String author, String source,
                      MapLocation location, PhotographDate date, ArrayList<String> tags,
                      String license) {
        setTitle(title);
        setDescription(description);
        setAuthor(author);
        setSource(source);
        setLocation(location);
        setDate(date);
        setTags(tags);
        setLicense(license);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    public String getSource() {
        return source;
    }


    public void setSource(String source) {
        this.source = source;
    }


    public MapLocation getLocation() {
        return location;
    }


    public void setLocation(MapLocation location) {
        this.location = location;
    }


    public PhotographDate getDate() {
        return date;
    }


    public void setDate(PhotographDate date) {
        this.date = date;
    }


    public ArrayList<String> getTags() {
        return tags;
    }


    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


    public double getDistance() {
        return distance;
    }


    public String getLicense() {
        return license;
    }


    public void setLicense(String license) {
        this.license = license;
    }


    public String getHumanDate(){
        if(isDated())   { return getDate().toString(); }
        else            { return null; }
    }


    public float getLatitude() {
        return location.getLat();
    }


    public float getLongitude() {
        return location.getLng();
    }


    public StorageReference lowQualityReference(){
        return FirebaseStorage.getInstance().getReference("low/" + getId());
    }


    public StorageReference highQualityReference(){
        return FirebaseStorage.getInstance().getReference("high/" + getId());
    }


    public String getWebUrl(@NotNull Context context){
        String url = context.getResources().getString(R.string.base_url);
        return url + "/photograph/" + getId();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    PARCELABLE                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    protected Photograph(@NotNull Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        author = in.readString();
        source = in.readString();
        location = in.readParcelable(MapLocation.class.getClassLoader());
        date = in.readParcelable(PhotographDate.class.getClassLoader());
        tags = in.createStringArrayList();
        distance = in.readDouble();
        license = in.readString();
    }


    public static final Creator<Photograph> CREATOR = new Creator<Photograph>() {
        @NotNull
        @Contract("_ -> new")
        @Override
        public Photograph createFromParcel(Parcel in) {
            return new Photograph(in);
        }

        @Override
        public Photograph[] newArray(int size) {
            return new Photograph[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(author);
        parcel.writeString(source);
        parcel.writeParcelable(location, i);
        parcel.writeParcelable(date, i);
        parcel.writeStringList(tags);
        parcel.writeDouble(distance);
        parcel.writeString(license);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          BOOLEANS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isLocalized() {
        return !Objects.isNull(location);
    }


    public boolean isDated() {
        return !Objects.isNull(date);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setDistanceTo(@NotNull Location point) {
        final int R = 6371;
        double latDistance = Math.toRadians(point.getLatitude() - location.getLat());
        double lonDistance = Math.toRadians(point.getLongitude() - location.getLng());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(location.getLat())) * Math.cos(Math.toRadians(point.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        distance = R * c * 1000;
    }


    public void SharePhotograph(Context context){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getWebUrl(context));
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, null));
    }


    public void openInBrowser(@NotNull Context context){
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getWebUrl(context))));
    }
}



