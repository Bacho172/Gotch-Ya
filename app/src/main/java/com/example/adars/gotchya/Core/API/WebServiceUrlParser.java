package com.example.adars.gotchya.Core.API;

import com.example.adars.gotchya.Core.Functions;

/**
 * Created by Adam Bachorz on 11.12.2018.
 */
public class WebServiceUrlParser {
    private String apiUrl;
    private Object[] routeParameters;

    public WebServiceUrlParser(Object... routeParameters) {
        this.apiUrl = "https://gotch-ya.herokuapp.com/api";
        this.routeParameters = routeParameters;
    }

    public String getURL() {
        return apiUrl + Functions.getDataVector(true, routeParameters);
    }

    public Object lastParameter() {
        return routeParameters[routeParameters.length - 1];
    }
}
