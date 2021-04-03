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

package com.singlevoid.caterina.data.filters;

import com.singlevoid.caterina.data.photograph.Photograph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterText extends FilterBase{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private String text = "";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs) {
        if  ( getText().isEmpty() )     { return photographs; }
        else                            { return  matchText(photographs); }
    }


    @Override
    public void setToDefault(){
        text = "";
    }


    @Override
    public boolean isDefault(){
        return text.equals("");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public String getText(){
        return text;
    }


    public void setText(String text){
        this.text = text;
    }


    @NotNull
    private ArrayList<Photograph> matchText(@NotNull ArrayList<Photograph> photographs) {
        ArrayList<Photograph> filteredPhotographs= new ArrayList<>();
        for (Photograph photograph : photographs) {
            if ( photographsContainsText(photograph) ) {
                filteredPhotographs.add(photograph);
            }
        }
        return filteredPhotographs;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          BOOLEANS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean photographsContainsText(@NotNull Photograph photograph) {
        return photographTitleContainsText(photograph) || photographDescriptionContainsText(photograph);
    }


    private boolean photographTitleContainsText(@NotNull Photograph photograph) {
        return photograph.getTitle().toUpperCase().contains(getText().toUpperCase());
    }


    private boolean photographDescriptionContainsText(@NotNull Photograph photograph) {
        return photograph.getDescription().toUpperCase().contains( getText().toUpperCase() );
    }
}
