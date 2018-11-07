package com.example.adars.gotchya;

import android.app.Activity;
import android.content.Context;

import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.DataModel.DomainModel.User;

import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.*;
/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public class FunctionsUnitTest {

    public  FunctionsUnitTest() {

    }

    @Test
    public void correct_createDataVector() {
        assertEquals("dataA;dataB;1", Functions.getDataVector("dataA","dataB",1));
    }

}
