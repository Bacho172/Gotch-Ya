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
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        latitute = 0;
        longitute = 0;
    }
    public double getLatitute(){
        return latitute;
    }
    public double getLongitute(){
        return longitute;
    }
    @Override
    public void onLocationChanged(Location location) {

        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            location = lastLocation;
        } else {
            location = locationManager.getLastKnownLocation(provider);
        }
        longitute = location.getLongitude();
        latitute = location.getLatitude();
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
