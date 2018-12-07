package com.example.adars.gotchya.Sensors;
import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

public class Misc extends Application {
    public static final String permissions[]={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,};
    public  void requestNeddedPermissions() {
    }
}
