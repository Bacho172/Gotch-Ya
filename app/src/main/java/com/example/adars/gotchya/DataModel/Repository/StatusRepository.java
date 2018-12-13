package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.Core.API.WebServiceAccess;
import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.DataModel.DomainModel.Status;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Adam Bachorz on 10.12.2018.
 */
public final class StatusRepository implements IRepository<Status> {

    private static StatusRepository instance;
    public static StatusRepository getInstance() {
        if (instance == null) {
            instance = new StatusRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<Status> getAll() {
        return null;
    }


    @Override
    public void insert(Status entity) {

    }

    @Override
    public void update(Status entity) {

    }

    @Override
    public void delete(Status entity) {

    }

    @Override
    public String getTableName() {
        return "statuses";
    }
}
