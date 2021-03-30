package com.singlevoid.caterina.data.filters;

import com.singlevoid.caterina.data.author.Author;
import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;

public class FilterAuthor extends FilterBase {

    public static final int MATCH_ANY = 40;
    private int filterMode = MATCH_ANY;


    FilterAuthor() {
        super();
    }


    @Override
    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs) {
        if  (getActiveFilters().isEmpty())  {return photographs;}
        else                                {return  matchAnyFilter(photographs);}
    }


    public void setAuthors(ArrayList<Author> authors){
        getOptions().clear();
        for(Author author: authors){
            getOptions().add(new FilterOption(author.getName(), author.getId()));
        }
    }


    private ArrayList<Photograph> matchAnyFilter(ArrayList<Photograph> photographs){
        ArrayList<Photograph> filtered = new ArrayList<>();
        for(Photograph photograph: photographs){
            boolean authorMatch = false;
            for(FilterOption option: getActiveFilters()){
                if(photograph.getAuthor() != null && photograph.getAuthor().equals(option.getId())){
                    authorMatch = true;
                    break;
                }
            }
            if(authorMatch){filtered.add(photograph);}
        }
        return filtered;

    }


    public void setFilterMode(int mode){
        filterMode = mode;
    }


    public int getFilterMode() {
        return filterMode;
    }


    @Override
    public boolean isDefault(){
        return super.isDefault() && filterMode == MATCH_ANY;
    }


    @Override
    public void setToDefault() {
        super.setToDefault();
        filterMode = MATCH_ANY;
    }
}
