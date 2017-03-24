package com.ymhou.model;

/**
 * Created by ymhou on 2017/3/24.
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name){
        this.name = name;
    }

    public String getDescription(){
        return "this is"+name;
    }
}
