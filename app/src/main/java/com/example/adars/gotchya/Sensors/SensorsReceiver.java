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

public class SensorsReceiver extends BroadcastReceiver {
    List<Address> addresses;
    static Sensors_data sensors_data = new Sensors_data();
    static Sensors_data getSensors_data() {
        return sensors_data;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      /*
        if (intent.getAction() == "accelometer_update") {
            String isMoving = intent.getStringExtra("isMoving");
            sensors_data.isMoving = isMoving;
            Toast.makeText(context, sensors_data.isMoving, Toast.LENGTH_SHORT).show();
        }
        */
      //if (intent.getAction() == "gps_update") {
            String latitude = intent.getStringExtra("latitude");
            String longitude = intent.getStringExtra("longitude");
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
            Intent i = new Intent("new_data");
            context.sendBroadcast(i);
    //    }

    }

}