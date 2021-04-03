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

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DateRange implements Parcelable {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private int start;
    private int end;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public DateRange() { }


    public DateRange(int start, int end){
        this.setStart(start);
        this.setEnd(end);
    }


    @SuppressLint("DefaultLocale")
    @NotNull
    public String toString(){
        if( isEmpty() )         { return ""; }
        else if (!isRanged())   { return String.valueOf(getStart()); }
        else                    { return String.format("[ %d - %d ]", getStart(), getEnd()); }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setStart(int start) {
        this.start = start;
    }


    public int getStart() {
        return start;
    }


    public void setEnd(int end) {
        this.end = end;
    }


    public long getEnd() {
        return end;
    }


    public boolean isEmpty() {
        return start == 0;
    }


    public boolean isRanged() {
        return start != 0 && end != 0;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    PARCELABLE                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    protected DateRange(@NotNull Parcel in) {
        start = in.readInt();
        end = in.readInt();
    }


    public static final Creator<DateRange> CREATOR = new Creator<DateRange>() {


        @NotNull
        @Contract("_ -> new")
        @Override
        public DateRange createFromParcel(Parcel in) {
            return new DateRange(in);
        }


        @NotNull
        @Contract(value = "_ -> new", pure = true)
        @Override
        public DateRange[] newArray(int size) {
            return new DateRange[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        parcel.writeInt(start);
        parcel.writeInt(end);
    }
}