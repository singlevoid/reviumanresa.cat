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

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PhotographDate implements Parcelable {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private DateRange year;
    private DateRange month;
    private DateRange day;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public PhotographDate() { }


    public PhotographDate(DateRange year, DateRange month, DateRange day) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }


    @Override
    @NotNull
    public String toString() {
        String date = "";
        if (!dayIsEmpty())      { date = addDay(date); }
        if (!monthIsEmpty())    { date = addMonth(date); }
        if (!yearIsEmpty())     { date = addYear(date); }
        return date;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setYear(DateRange year) {
        this.year = year;
    }


    public DateRange getYear() {
        return year;
    }


    public DateRange getMonth() {
        return month;
    }


    public void setMonth(DateRange month) {
        this.month = month;
    }


    public DateRange getDay() {
        return day;
    }


    public void setDay(DateRange day) {
        this.day = day;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    PARCELABLE                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    protected PhotographDate(@NotNull Parcel in) {
        year = in.readParcelable(DateRange.class.getClassLoader());
        month = in.readParcelable(DateRange.class.getClassLoader());
        day = in.readParcelable(DateRange.class.getClassLoader());
    }


    public static final Creator<PhotographDate> CREATOR = new Creator<PhotographDate>() {

        @NotNull
        @Contract("_ -> new")
        @Override
        public PhotographDate createFromParcel(Parcel in) {
            return new PhotographDate(in);
        }


        @NotNull
        @Contract(value = "_ -> new", pure = true)
        @Override
        public PhotographDate[] newArray(int size) {
            return new PhotographDate[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        parcel.writeParcelable(year, i);
        parcel.writeParcelable(month, i);
        parcel.writeParcelable(day, i);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @NotNull
    private String addDay(String date){
        return addDateSeparatorIfNotEmpty(date) + getDay().toString();
    }


    @NotNull
    private String addMonth(String date){
        return addDateSeparatorIfNotEmpty(date) + getMonth().toString();
    }


    @NotNull
    private String addYear(String date){
        return addDateSeparatorIfNotEmpty(date) + getYear().toString();
    }


    @NotNull
    @Contract(pure = true)
    private String addDateSeparatorIfNotEmpty(@NotNull String date){
        if(!date.isEmpty())     { return date + " / "; }
        else                    { return date; }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   BOOLEANS                                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean dayIsEmpty(){
        return Objects.isNull(getDay());
    }

    private boolean monthIsEmpty(){
        return Objects.isNull(getMonth());
    }

    private boolean yearIsEmpty(){
        return Objects.isNull(getYear());
    }
}
