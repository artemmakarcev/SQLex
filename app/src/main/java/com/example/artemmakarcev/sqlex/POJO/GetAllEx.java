package com.example.artemmakarcev.sqlex.POJO;

public class GetAllEx {
    private Database database;
    private String name;
    private String description;
    private int id;

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

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return
                "Response{" +
                        "database = '" + database + '\'' +
                        ",name = '" + name + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
