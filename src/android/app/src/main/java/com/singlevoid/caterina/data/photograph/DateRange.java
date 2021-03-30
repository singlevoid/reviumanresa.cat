package com.singlevoid.caterina.data.photograph;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DateRange implements Parcelable {

    private long start;
    private long end;

    public DateRange(){}
    public DateRange(long start, long end){
        this.setStart(start);
        this.setEnd(end);
    }

    protected DateRange(Parcel in) {
        start = in.readLong();
        end = in.readLong();
    }

    public static final Creator<DateRange> CREATOR = new Creator<DateRange>() {
        @Override
        public DateRange createFromParcel(Parcel in) {
            return new DateRange(in);
        }

        @Override
        public DateRange[] newArray(int size) {
            return new DateRange[size];
        }
    };

    public void setStart(Long start) {
        this.start = start;
    }
    public void setEnd(Long end) {
        this.end = end;
    }

    public long getStart() {return start;}
    public long getEnd() {return end;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(start);
        parcel.writeLong(end);
    }


    public boolean isEmpty(){
        return start == -1;
    }

    public boolean isRanged(){
        return start != -1 && end != -1;
    }

    @NotNull
    public String toString(){
        if(isEmpty()) {return ""; }
        else if (!isRanged()) { return "" + start; }
        else{ return "[" + start + " - " + end +"]"; }
    }
}