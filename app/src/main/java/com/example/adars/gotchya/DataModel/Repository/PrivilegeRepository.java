package com.example.adars.gotchya.DataModel.Repository;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class PrivilegeRepository implements IRepository {

    private static PrivilegeRepository instance;
    public static PrivilegeRepository getInstance() {
        if (instance == null) {
            instance = new PrivilegeRepository();
        }
        return instance;
    }

    @Override
    public ArrayList getAll() {
        return null;
    }

    @Override
    public Object getOneByID(Integer ID) {
        return null;
    }

    @Override
    public void insert(Object entity) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public String getTableName() {
        return "privileges";
    }
}
