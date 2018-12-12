package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 12.12.2018.
 */
public final class ApplicationReportRepository implements IRepository<ApplicationReport> {

    private static ApplicationReportRepository instance;
    public static ApplicationReportRepository getInstance() {
        if (instance == null) {
            instance = new ApplicationReportRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<ApplicationReport> getAll() {
        return null;
    }

    @Override
    public ApplicationReport getOneByID(Integer ID) {
        return null;
    }

    @Override
    public void insert(ApplicationReport entity) {

    }

    @Override
    public void update(ApplicationReport entity) {

    }

    @Override
    public void delete(ApplicationReport entity) {

    }

    @Override
    public String getTableName() {
        return "application_reports";
    }
}
