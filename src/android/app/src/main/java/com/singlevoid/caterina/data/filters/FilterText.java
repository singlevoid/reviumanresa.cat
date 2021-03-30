package com.singlevoid.caterina.data.filters;

import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;

public class FilterText extends FilterBase{


    private String text = "";


    @Override
    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs) {
        if  (getText().isEmpty())           {return photographs;}
        else                                {return  matchText(photographs);}
    }


    public String getText(){
        return text;
    }


    public void setText(String text){
        this.text = text;
    }

    public boolean isDefault(){
        return text.equals("");
    }


    public void setToDefault(){
        text = "";
    }

    private ArrayList<Photograph> matchText(ArrayList<Photograph> photographs) {
        ArrayList<Photograph> filtered = new ArrayList<>();
        for (Photograph photograph : photographs) {
            if(photograph.getDescription().toUpperCase().contains(getText().toUpperCase())
                    || photograph.getTitle().toUpperCase().contains(getText().toUpperCase())){
                filtered.add(photograph);
            }
        }
        return filtered;
    }
}
