package com.example.adars.gotchya.DataModel.DomainModel;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public class Status extends Entity {
    private String name;
    private ArrayList<User> users;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
