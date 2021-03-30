package com.singlevoid.caterina.data.filters;

public class FilterOption {

    private String value;
    private boolean active = false;
    private String id;

    FilterOption(){}

    FilterOption(String value){
        this.value = value;
    }

    FilterOption(String value, String id){
        this.setValue(value);
        this.setId(id);
    }

    public String getValue() {return value;}
    public String getId() {return id;}
    public boolean isActive() {return active;}

    public void setValue(String value){ this.value = value;}
    public void setActive(boolean status) {this.active = status;}
    public void setId(String id) {this.id = id;}
    public void invertStatus() {this.active = !this.active;}

}
