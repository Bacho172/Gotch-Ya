package com.example.adars.gotchya.Sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SensorsDataCreator {
    static Sensors_data createSensorData(Context context, String longitude, String latitude) {
        List<Address> addresses = null;
        Sensors_data sensors_data = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sensors_data.address = addresses.get(0).getAddressLine(0);
        sensors_data.city = addresses.get(0).getLocality();
        sensors_data.country = addresses.get(0).getCountryName();
        sensors_data.postalCode = addresses.get(0).getPostalCode();
        sensors_data.latitude = latitude;
        sensors_data.longitde = longitude;
        return sensors_data;
    }
}
