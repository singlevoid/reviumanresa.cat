package com.singlevoid.caterina.data.author;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class AuthorManager {


    private final ArrayList<Author> authors = new ArrayList<>();


    public void addAuthor(Author author) {
        authors.add(author);
        sortAuthors();
    }


    public void sortAuthors(){
        Collections.sort(authors);
    }


    public ArrayList<Author> getAuthors() {return authors; }


    public Author getAuthorById(String id) {
        for(Author author: authors){
            if(author.getId().equals(id)){
                return author;
            }
        }
        return null;
    }


    public AuthorManager parseQuery(QuerySnapshot query){
        for (QueryDocumentSnapshot document : query){
            Author author = document.toObject(Author.class);
            author.setId(document.getId());
            addAuthor(author);
        }
        return this;
    }
}
