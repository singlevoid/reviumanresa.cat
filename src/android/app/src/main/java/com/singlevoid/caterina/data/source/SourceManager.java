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

package com.singlevoid.caterina.data.source;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SourceManager {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final ArrayList<Source> sources = new ArrayList<>();


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void addSource(Source source){
        sources.add(source);
    }


    public ArrayList<Source> getSources(){
        return sources;
    }


    public Source getSourceById(String id){
        for(Source source: sources){
            if(source.getId().equals(id)){
                return source;
            }
        }
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public SourceManager parseQuery(@NotNull QuerySnapshot query){
        for (QueryDocumentSnapshot document : query){
            Source source = document.toObject(Source.class);
            source.setId(document.getId());
            addSource(source);
        }
        return this;
    }
}
