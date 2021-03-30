package com.singlevoid.caterina.data.filters;

import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.data.tag.Tag;

import java.util.ArrayList;

public class FilterTag extends FilterBase {


    public static int MATCH_ALL = 30;
    public static int MATCH_ANY = 31;

    private int mode = MATCH_ALL;


    public void setStatus(FilterOption option, Boolean status){
        option.setActive(status);
    }


    public void setFilterMode(int mode){
        this.mode = mode;
    }


    public int getFilterMode(){
        return mode;
    }


    public ArrayList<Photograph> filter(ArrayList<Photograph> photographs){
        if  (getActiveFilters().isEmpty())  {return photographs;}
        if          (mode == MATCH_ANY)     {return matchAllFilter(photographs);}
        else if     (mode == MATCH_ALL)     {return matchAnyFilter(photographs);}
        else                                {return  new ArrayList<>();}
    }


    private ArrayList<Photograph> matchAllFilter(ArrayList<Photograph> photographs){
        ArrayList<Photograph> filtered = new ArrayList<>();
        for(Photograph photograph: photographs){
            for(FilterOption option: getActiveFilters()){
                if(photograph.getTags().contains(option.getId())){
                    filtered.add(photograph);
                    break;
                }
            }
        }
        return filtered;
    }


    private ArrayList<Photograph> matchAnyFilter(ArrayList<Photograph> photographs){
        ArrayList<Photograph> filtered = new ArrayList<>();
        for(Photograph photograph: photographs){
            boolean all = true;
            for(FilterOption option: getActiveFilters()){
                if(!photograph.getTags().contains(option.getId())){
                    all = false;
                    break;
                }
            }
            if(all){filtered.add(photograph);}
        }
        return filtered;

    }


    public void setTags(ArrayList<Tag> tags){
        getOptions().clear();
        for(Tag tag: tags){
            getOptions().add(new FilterOption(tag.getName(), tag.getId()));
        }
    }


    @Override
    public boolean isDefault(){
        return super.isDefault() && mode == MATCH_ALL;
    }


    @Override
    public void setToDefault() {
        super.setToDefault();
        mode = MATCH_ALL;
    }
}
