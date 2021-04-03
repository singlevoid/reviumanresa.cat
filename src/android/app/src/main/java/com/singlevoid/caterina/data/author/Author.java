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

package com.singlevoid.caterina.data.author;

import com.singlevoid.caterina.data.photograph.DateRange;

public class Author implements Comparable<Author>{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private String id;
    private String name;
    private String description;
    private DateRange date;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public Author() { }


    public Author(String name, String description, DateRange date) {
        this.setName(name);
        this.setDescription(description);
        this.setDate(date);
    }


    @Override
    public int compareTo(Author author) {
        return getName().compareTo(author.getName());
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setDate(DateRange date) {
        this.date = date;
    }


    public String getId() {
        return this.id;
    }


    public String getName() {
        return this.name;
    }


    public String getDescription() {
        return this.description;
    }


    public DateRange getDate() {
        return this.date;
    }
}
