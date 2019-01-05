package com.example.adars.gotchya.Sensors;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import com.example.adars.gotchya.R;

/**
 * Created by Adam Bachorz on 29.12.2018.
 */
public class LocationCaller implements LocationListener {

    private LocationManager locationManager;
    private double longitude;
    private double latitude;
    private String longitudeDirection;
    private String latitudeDirection;

    public LocationCaller(Activity activity) {
        locationManager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Location location;

        boolean accessFineLocationGranted =
                ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean accessCoarseLocationGranted =
                ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (!accessFineLocationGranted && !accessCoarseLocationGranted) {
            Snackbar.make(activity.findViewById(R.id.main_menu_layout), "Brak włączonej lokalizacji", Snackbar.LENGTH_SHORT).show();
        }

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateLocation(location);
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
    }

    private void updateLocation(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        longitudeDirection = longitude < 0 ? "W" : "E";
        latitudeDirection = latitude < 0 ? "S" : "N";
    }

    public String getCoordinates() {
        return latitude + latitudeDirection + longitude + longitudeDirection;
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

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getLongitudeDirection() {
        return longitudeDirection;
    }

    public String getLatitudeDirection() {
        return latitudeDirection;
    }
}
