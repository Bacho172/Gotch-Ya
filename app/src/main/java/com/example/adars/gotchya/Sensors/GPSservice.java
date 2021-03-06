package com.example.adars.gotchya.Sensors;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class GPSservice extends Service {
    public static final String GPS_SERVICE_INTENT = "gps_update";
    public static final String GPS_SERVICE_INTENT_EXTRA_LONGITUDE = "longitude";
    public static final String GPS_SERVICE_INTENT_EXTRA_LATITUDE = "latitude";
    private LocationManager locationManager = null;
    private GPS gps;
    private static final int INTERVAL = 1000;
    private static final float D_DISTANS = 0;

    private class GPS implements LocationListener {
        Location lastLocation;

        public GPS(String provider) {
            lastLocation = new Location(provider);
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }

        public Location getLastLocation() {
            return lastLocation;
        }

        @Override
        public void onLocationChanged(Location location) {
            lastLocation.set(location);
            Intent i = new Intent(GPS_SERVICE_INTENT);
            i.putExtra(GPS_SERVICE_INTENT_EXTRA_LATITUDE, String.valueOf(location.getLatitude()));
            i.putExtra(GPS_SERVICE_INTENT_EXTRA_LONGITUDE, String.valueOf(location.getLongitude()));
            sendBroadcast(i);
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        gps = new GPS(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVAL, D_DISTANS, gps);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
