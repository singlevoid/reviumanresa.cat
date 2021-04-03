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

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class AuthorManager {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final ArrayList<Author> authors = new ArrayList<>();


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void addAuthor(Author author) {
        authors.add(author);
        sortAuthors();
    }


    public void sortAuthors() {
        Collections.sort(authors);
    }


    public ArrayList<Author> getAuthors() {
        return authors;
    }


    public Author getAuthorById(String id) {
        for( Author author: authors ){
            if( author.getId().equals(id) ){
                return author;
            }
        }
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          METHODS                                           //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public AuthorManager parseQuery(QuerySnapshot query) {
        for ( QueryDocumentSnapshot document : query) {
            Author author = document.toObject(Author.class);
            author.setId(document.getId());
            addAuthor(author);
        }
        return this;
    }
}
