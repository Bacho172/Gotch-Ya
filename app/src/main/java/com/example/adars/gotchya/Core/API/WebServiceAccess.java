package com.example.adars.gotchya.Core.API;

import com.example.adars.gotchya.Core.Functions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Adam Bachorz on 12.12.2018.
 */
public class WebServiceAccess {

    private String apiUrl;
    private Object[] routeParameters;

    public WebServiceAccess(Object... routeParameters) {
        this.apiUrl = "https://gotch-ya.herokuapp.com/api";
        this.routeParameters = routeParameters;
    }

    public boolean accessDenied() {
        try {
            JSONObject object = JSONHelper.readJsonFromUrl(getURL());
            return object.getBoolean("status");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            return false;
        }
        return false;
    }

    public JSONArray getJsonArray() {
        if (accessDenied()) return null;
        try {
            return JSONHelper.readJsonFromUrlToArray(getURL());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getURL() {
        return apiUrl + Functions.getDataVector(true, routeParameters);
    }

    public Object lastParameter() {
        return routeParameters[routeParameters.length - 1];
    }
}
