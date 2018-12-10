package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.DataModel.DomainModel.Device;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public class DeviceRepository implements IRepository<Device> {

    @Override
    public ArrayList<Device> getAll() {
        return null;
    }

    @Override
    public Device getOneByID(Integer ID) {
        return null;
    }

    @Override
    public void insert(Device entity) {

    }

    @Override
    public void update(Device entity) {

    }

    @Override
    public void delete(Device entity) {

    }

    @Override
    public String getTableName() {
        return "devices";
    }
}
