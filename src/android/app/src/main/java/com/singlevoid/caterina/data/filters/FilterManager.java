package com.singlevoid.caterina.data.filters;

import android.content.Context;

import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;


public class FilterManager {

    private final FilterLocation location;
    private final FilterSort sort;
    private final FilterTag tag;
    private final FilterAuthor author;
    private final FilterText text;

    public FilterManager(Context context){
        location = new FilterLocation(context);
        sort = new FilterSort(context);
        tag = new FilterTag();
        author = new FilterAuthor();
        text = new FilterText();
    }

    public FilterLocation getFilterLocation(){ return location;}
    public FilterSort getSort(){ return sort;}
    public FilterTag getTag(){ return tag;}
    public FilterAuthor getAuthor(){ return author;}


    public FilterText getText() {
        return text;
    }

    public boolean isDefault(){
        if(!getFilterLocation().isDefault()){
            return false;
        }

        else if(!getTag().isDefault()){
            return false;
        }

        else if(!getAuthor().isDefault()){
            return false;
        }

        else if(!getText().isDefault()){
            return false;
        }

        else{
            return true;
        }

    }

    public void setToDefault(){
        getFilterLocation().setToDefault();
        getTag().setToDefault();
        getAuthor().setToDefault();
        getText().setToDefault();
    }


    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs){
        ArrayList<Photograph> filtered = photographs;

        filtered = text.filter(filtered);
        filtered = location.filter(filtered);
        filtered = tag.filter(filtered);
        filtered = author.filter(filtered);
        filtered = sort.filter(filtered);

        return filtered;
    }
}
