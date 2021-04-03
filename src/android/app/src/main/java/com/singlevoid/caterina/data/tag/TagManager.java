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

package com.singlevoid.caterina.data.tag;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TagManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private final ArrayList<Tag> tags = new ArrayList<>();


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void addTag(Tag tag) {
        tags.add(tag);
    }


    public ArrayList<Tag> getAllTags(){
        return tags;
    }


    public ArrayList<Tag> getTags(@NotNull ArrayList<String> tags){
        ArrayList<Tag> filteredTags = new ArrayList<>();
        for (String id: tags){
            for(Tag tag : this.tags){
                if(tag.getId().equals(id)) {
                    filteredTags.add(tag);
                }
            }
        }
        return filteredTags;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public TagManager parseQuery(@NotNull QuerySnapshot query){
        for (QueryDocumentSnapshot document: query){
            Tag tag = document.toObject(Tag.class);
            tag.setId(document.getId());
            addTag(tag);
        }
        return this;
    }

}
