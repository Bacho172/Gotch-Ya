package com.example.adars.gotchya.DataModel.DataModel;

/**
 * Created by Adam Bachorz on 12.12.2018.
 */
public final class ApplicationReportModel {

    private static ApplicationReportModel instance;
    public static ApplicationReportModel getInstance() {
        if (instance == null) {
            instance = new ApplicationReportModel();
        }
        return instance;
    }


}
