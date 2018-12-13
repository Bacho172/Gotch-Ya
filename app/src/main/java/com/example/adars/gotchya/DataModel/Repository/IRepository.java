package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.DataModel.DomainModel.Entity;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 07.11.2018.
 */
public interface IRepository<E> {
    ArrayList<E> getAll();
    default E getOneByID(Integer ID) {
        for (E entity : getAll()) {
            if (((Entity) entity).getID() == ID) return entity;
        }
        return null;
    }
    void insert(E entity);
    void update(E entity);
    void delete(E entity);
    String getTableName();

    default String simpleSelect() {
        return "SELECT * FROM " + getTableName();
    }
}
