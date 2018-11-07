package com.example.adars.gotchya.DataModel.DomainModel;

/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public abstract class Entity {
    protected Integer ID;

    public Entity(){}
    public Entity(Integer ID){
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
