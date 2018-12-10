package com.example.adars.gotchya.DataModel.DataModel;

import com.example.adars.gotchya.DataModel.Repository.UserRepository;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class PrivilegeModel {

    private static PrivilegeModel instance;
    public static PrivilegeModel getInstance() {
        if (instance == null) {
            instance = new PrivilegeModel();
        }
        return instance;
    }


}
