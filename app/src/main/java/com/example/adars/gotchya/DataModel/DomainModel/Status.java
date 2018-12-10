package com.example.adars.gotchya.DataModel.DomainModel;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public class Status extends Entity {
    private String name;

    public Status() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
