package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.DataModel.DomainModel.Device;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class DeviceRepository implements IRepository<Device> {

    private static DeviceRepository instance;
    public static DeviceRepository getInstance() {
        if (instance == null) {
            instance = new DeviceRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<Device> getAll() {
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
