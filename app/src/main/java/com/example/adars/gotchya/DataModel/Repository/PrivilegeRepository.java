package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.DataModel.DomainModel.Privilege;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class PrivilegeRepository implements IRepository<Privilege> {

    private static PrivilegeRepository instance;
    public static PrivilegeRepository getInstance() {
        if (instance == null) {
            instance = new PrivilegeRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<Privilege> getAll() {
        return null;
    }

    @Override
    public Privilege getOneByID(Integer ID) {
        return null;
    }

    @Override
    public void insert(Privilege entity) {

    }

    @Override
    public void update(Privilege entity) {

    }

    @Override
    public void delete(Privilege entity) {

    }

    @Override
    public String getTableName() {
        return "privileges";
    }
}
