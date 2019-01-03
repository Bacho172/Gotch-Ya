package com.example.adars.gotchya.Sensors;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SensorsDataCreator {
    public static Sensors_data createSensorData(Context context, String latitude, String longitude) {
        List<Address> addresses = null;
        Sensors_data sensors_data = new Sensors_data();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sensors_data.setAddress(addresses.get(0).getAddressLine(0));
        sensors_data.setCity(addresses.get(0).getLocality());
        sensors_data.setCountry(addresses.get(0).getCountryName());
        sensors_data.setPostalCode(addresses.get(0).getPostalCode());
        sensors_data.setLatitude(latitude);
        sensors_data.setLongitde(longitude);
        return sensors_data;
    }
}
