package com.singlevoid.caterina.data.tag;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TagManager {

    private final ArrayList<Tag> tags = new ArrayList<>();

    public void addTag(Tag tag) {tags.add(tag);}

    public ArrayList<Tag> getTags(){ return tags;}

    public ArrayList<Tag> getTags(ArrayList<String> tags){
        ArrayList<Tag> filteredTags = new ArrayList<>();
        for (String id: tags){
            for(Tag tag : this.tags){
                if(tag.getId().equals(id)){filteredTags.add(tag);}
            }
        }
        return filteredTags;
    }

    public TagManager parseQuery(QuerySnapshot query){
        for (QueryDocumentSnapshot document: query){
            Tag tag = document.toObject(Tag.class);
            tag.setId(document.getId());
            addTag(tag);
        }
        return this;
    }

}
