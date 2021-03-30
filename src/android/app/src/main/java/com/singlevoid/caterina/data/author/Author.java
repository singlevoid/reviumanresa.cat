package com.singlevoid.caterina.data.author;

import com.singlevoid.caterina.data.photograph.DateRange;

public class Author implements Comparable<Author>{


    private String id;
    private String name;
    private String description;
    private DateRange date;


    public Author(){}


    public Author(String name, String description, DateRange date){
        this.setName(name);
        this.setDescription(description);
        this.setDate(date);
    }


    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) {this.description = description; }
    public void setDate(DateRange date) {this.date = date; }


    public String getId() {return this.id; }
    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public DateRange getDate() { return this.date; }


    @Override
    public int compareTo(Author author) {
        return getName().compareTo(author.getName());
    }

}
