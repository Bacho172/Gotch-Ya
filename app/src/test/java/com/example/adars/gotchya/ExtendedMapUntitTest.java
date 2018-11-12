package com.example.adars.gotchya;

import android.content.Intent;

import com.example.adars.gotchya.Core.ExtendedMap;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Adam Bachorz on 11.11.2018.
 */
public class ExtendedMapUntitTest {

    ExtendedMap<Integer, String, Object> map = new ExtendedMap<>();
    String[] keys = {"K1", "K2", "K3"};
    Object[] valuesC1 = {true, 1, "C1C"};
    Object[] valuesC2 = {false, 2, "Inna"};
    Intent propObject1 = new Intent();
    Intent propObject2 = new Intent();
    int hash1 = System.identityHashCode(propObject1);
    int hash2 = System.identityHashCode(propObject2);

    @Test
    public void corret_ExtendedMapValuesKeeping(){

        map.put(hash1, keys, valuesC1);
        map.put(hash2, keys, valuesC2);

        assertEquals(true, map.get(hash1, "K1"));
        assertEquals(false, map.get(hash2, "K1"));
        assertNotEquals(2, map.get(hash1, "K2"));

    }

    @Test
    public void correct_PutSingleValue(){
        map.put(hash1, keys, valuesC1);
        map.put(hash2, keys, valuesC2);
        String newValue1 = "New value";
        String newValue2 = "New value other";


        map.put(hash1, "K3", newValue1);
        map.put(hash2, "K3", newValue2);

        assertEquals(newValue1, map.get(hash1, "K3"));
        assertEquals(newValue2, map.get(hash2, "K3"));


    }
}
