package com.example.adars.gotchya.DataModel.DomainModel;

import java.util.Date;

/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public abstract class Entity {
    protected Integer ID;
    protected Date createdAt;
    protected Date updatedAt;


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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
