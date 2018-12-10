package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.DataModel.DomainModel.Status;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class StatusRepository implements IRepository<Status> {

    private static StatusRepository instance;
    public static StatusRepository getInstance() {
        if (instance == null) {
            instance = new StatusRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<Status> getAll() {
        return null;
    }

    @Override
    public Status getOneByID(Integer ID) {
        return null;
    }

    @Override
    public void insert(Status entity) {

    }

    @Override
    public void update(Status entity) {

    }

    @Override
    public void delete(Status entity) {

    }

    @Override
    public String getTableName() {
        return "statuses";
    }
}
