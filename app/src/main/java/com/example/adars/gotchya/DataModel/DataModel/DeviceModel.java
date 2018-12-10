package com.example.adars.gotchya.DataModel.DataModel;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class DeviceModel {

    private static DeviceModel instance;
    public static DeviceModel getInstance() {
        if (instance == null) {
            instance = new DeviceModel();
        }
        return instance;
    }


}
