package com.singlevoid.caterina.data.photograph;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PhotographDate implements Parcelable {

    private DateRange year;
    private DateRange month;
    private DateRange day;

    public PhotographDate(){}
    public PhotographDate(DateRange year, DateRange month, DateRange day){
        this.year = year; this.month = month; this.day = day;
    }


    protected PhotographDate(Parcel in) {
        year = in.readParcelable(DateRange.class.getClassLoader());
        month = in.readParcelable(DateRange.class.getClassLoader());
        day = in.readParcelable(DateRange.class.getClassLoader());
    }


    public static final Creator<PhotographDate> CREATOR = new Creator<PhotographDate>() {
        @Override
        public PhotographDate createFromParcel(Parcel in) {
            return new PhotographDate(in);
        }

        @Override
        public PhotographDate[] newArray(int size) {
            return new PhotographDate[size];
        }
    };


    public DateRange getYear() {return year;}
    public DateRange getMonth() {return month;}
    public DateRange getDay() {return day;}


    public void setYear(DateRange year) {this.year = year;}
    public void setMonth(DateRange month) {this.month = month;}
    public void setDay(DateRange day) {this.day = day;}


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(year, i);
        parcel.writeParcelable(month, i);
        parcel.writeParcelable(day, i);
    }


    @NotNull
    public String toString() {
        String string = "";
        if (!Objects.isNull(day.toString())) {
            string = string  +  day.toString();}
        if (!Objects.isNull(month.toString())) {
            if(!Objects.isNull(string)){ string += "/";}
            string = string  +  month.toString();}
        if (!Objects.isNull(year.toString())) {
            if(!Objects.isNull(string)){ string += "/";}
                string += year.toString();
        }
        return string;
    }

}
