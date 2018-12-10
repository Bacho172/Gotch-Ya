package com.example.adars.gotchya.DataModel.DataModel;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class StatusModel {

    private static StatusModel instance;
    public static StatusModel getInstance() {
        if (instance == null) {
            instance = new StatusModel();
        }
        return instance;
    }
}
