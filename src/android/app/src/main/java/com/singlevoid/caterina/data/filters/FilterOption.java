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

public class FilterOption {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private String value;
    private boolean active = false;
    private String id;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    FilterOption(String value, String id){
        this.setValue(value);
        this.setId(id);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public String getValue() {
        return value;
    }


    public String getId() {
        return id;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public void setActive(boolean status) {
        this.active = status;
    }


    public void setId(String id) {
        this.id = id;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   BOOLEANS                                                 //
    ////////////////////////////////////////////////////////////////////////////////////////////////



    public boolean isActive() {
        return active;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          METHODS                                           //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void invertStatus() {
        this.active = !this.active;
    }
}
