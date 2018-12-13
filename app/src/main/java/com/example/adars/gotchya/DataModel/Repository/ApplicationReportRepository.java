package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.Core.API.WebServiceAccess;
import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;

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
    public void insert(ApplicationReport entity) {
        try {
            WebServiceAccess access = new WebServiceAccess(getTableName());
            URL url = new URL(access.getURL());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("kolumna","wartość"));

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(Functions.getQuery(parameters));
            writer.flush();
            writer.close();
            outputStream.close();

            connection.connect();
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
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
