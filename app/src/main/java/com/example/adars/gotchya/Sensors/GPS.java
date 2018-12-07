package com.example.adars.gotchya.Sensors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class GPS implements LocationListener {
    private double latitute;
    private double longitute;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private Context context;
    private Location lastLocation;

    public GPS(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.context, "Not Enough Permission", Toast.LENGTH_SHORT).show();
        }

        latitute = 0;
        longitute = 0;
    }

    public double getLatitute() {
        latitute = this.location.getLatitude();
        return latitute;
    }

    public double getLongitute() {
        longitute = this.location.getLongitude();
        return longitute;
    }

    @Override
    public void onLocationChanged(Location location) {

        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.context, "Not Enough Permission", Toast.LENGTH_SHORT).show();
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        this.location=location;


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
