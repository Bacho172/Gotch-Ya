package com.example.adars.gotchya.DataModel.Repository;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 07.11.2018.
 */
public interface IRepository<E> {
    ArrayList<E> getAll();
    E getOneByID(Integer ID);
    void insert(E entity);
    void update(E entity);
    void delete(E entity);
    String getTableName();

    default String simpleSelect() {
        return "SELECT * FROM " + getTableName();
    }
}
