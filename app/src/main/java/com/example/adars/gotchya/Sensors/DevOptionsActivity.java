package com.example.adars.gotchya.Sensors;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adars.gotchya.R;

public class DevOptionsActivity extends AppCompatActivity {
    Button gps;
    Button accel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_options);
        accel=findViewById(R.id.buttonAccel);
        gps=findViewById(R.id.buttonGPS);
        ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET,},1);

        accel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
