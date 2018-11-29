package com.example.artemmakarcev.sqlex.POJO;

public class GetAllEx {
    private Database database;
    private String name;
    private String description;
    private String id;

    public GetAllEx(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setDatabase(Database database){
        this.database = database;
    }

    public Database getDatabase(){
        return database;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }


}
