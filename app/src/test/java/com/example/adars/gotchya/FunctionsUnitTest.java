package com.example.adars.gotchya;

import com.example.adars.gotchya.Core.API.WebServiceUrlParser;
import com.example.adars.gotchya.Core.Functions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public class FunctionsUnitTest {

    public  FunctionsUnitTest() {

    }

    @Test
    public void correct_createDataVector() {
        assertEquals("dataA;dataB;1", Functions.getDataVector(false,"dataA","dataB",1));
    }

    @Test
    public void correct_WebServiceURL() {
        WebServiceUrlParser parser = new WebServiceUrlParser("users");
        assertEquals("https://gotch-ya.herokuapp.com/api/users", parser.getURL());
    }

}
